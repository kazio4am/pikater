package pikater;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.proto.ContractNetInitiator;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import pikater.ontology.messages.BoolSItem;
import pikater.ontology.messages.CreateAgent;
import pikater.ontology.messages.Evaluation;
import pikater.ontology.messages.Execute;
import pikater.ontology.messages.ExecuteParameters;
import pikater.ontology.messages.FloatSItem;
import pikater.ontology.messages.GetAgents;
import pikater.ontology.messages.GetParameters;
import pikater.ontology.messages.Id;
import pikater.ontology.messages.IntSItem;
import pikater.ontology.messages.Options;
import pikater.ontology.messages.MessagesOntology;
import pikater.ontology.messages.Option;
import pikater.ontology.messages.Results;
import pikater.ontology.messages.SearchSolution;
import pikater.ontology.messages.SetSItem;
import pikater.ontology.messages.Task;

public class Agent_OptionsManager extends Agent {

	private static final long serialVersionUID = 7028866964341806289L;
	
	private Codec codec = new SLCodec();
	private Ontology ontology = MessagesOntology.getInstance();
	
	private List results = new ArrayList();
	
	protected pikater.ontology.messages.Evaluation evaluation;
	protected List Options;
	protected pikater.ontology.messages.Agent Agent;

	private int task_number = 0;
	private int max_number_of_tasks;
	private List query_queue = new ArrayList();
	private int number_of_current_tasks = 0;
	private List computing_agents;  // list of AIDs
	private Task received_task;
	private ACLMessage received_request = null;
	
	protected String getAgentType() {
		return "Option Manager";
	}
	
	protected class ExecuteTask extends ContractNetInitiator{

		private static final long serialVersionUID = -2044738642107219180L;

		int nResponders;
		ACLMessage query;
		ACLMessage cfp;
		
		public ExecuteTask(jade.core.Agent a, ACLMessage cfp, ACLMessage _query) {
			super(a, cfp);
			nResponders = computing_agents.size();
			query = _query;
			this.cfp = cfp;
		}

		protected void handlePropose(ACLMessage propose, Vector v) {
			System.out.println(myAgent.getLocalName()+": Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
		}
		
		protected void handleRefuse(ACLMessage refuse) {
			System.out.println(myAgent.getLocalName()+": Agent "+refuse.getSender().getName()+" refused");
		}
		
		protected void handleFailure(ACLMessage failure) {
			if (failure.getSender().equals(myAgent.getAMS())) {
				// FAILURE notification from the JADE runtime: the receiver
				// does not exist
				System.out.println("Responder does not exist");
			}
			else {
				System.out.println(myAgent.getLocalName()+": Agent "+failure.getSender().getName()+" failed");
			}
			// Immediate failure --> we will not receive a response from this agent
			nResponders--;
		}
		
		protected void handleAllResponses(Vector responses, Vector acceptances) {
			if (responses.size() < nResponders) {
				// Some responder didn't reply within the specified timeout
				System.out.println(myAgent.getLocalName()+": Timeout expired: missing "+(nResponders - responses.size())+" responses");
			}
			// Evaluate proposals.
			int bestProposal = Integer.MAX_VALUE;
			AID bestProposer = null;
			ACLMessage accept = null;
			Enumeration e = responses.elements();
			while (e.hasMoreElements()) {
				ACLMessage msg = (ACLMessage) e.nextElement();
				if (msg.getPerformative() == ACLMessage.PROPOSE) {
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
					acceptances.addElement(reply);
					int proposal = Integer.parseInt(msg.getContent());
					if (proposal < bestProposal) {
						bestProposal = proposal;
						bestProposer = msg.getSender();
						accept = reply;
					}
				}
			}
			// Accept the proposal of the best proposer
			if (accept != null) {
				System.out.println(myAgent.getLocalName()+": Accepting proposal "+bestProposal+" from responder "+bestProposer.getName());
								
				try {
					ContentElement content = getContentManager().extractContent(cfp);
					
					Execute execute = (Execute) (((Action) content).getAction());

					Action a = new Action();
					a.setAction(execute);
					a.setActor(myAgent.getAID());
												
					getContentManager().fillContent(accept, a);
				} catch (UngroundedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CodecException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (OntologyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);				
			}						
			// TODO - if there is no best proposer, return options to the queue
		}
		
		protected void handleInform(ACLMessage inform) {
			System.out.println(myAgent.getLocalName()+": Agent "+inform.getSender().getName()+" successfully performed the requested action");
			// send result to the search agent:
			
			// extract evaluation from the task in the inform message
			ContentElement content;
			try {
				content = getContentManager().extractContent(inform);
				ContentElement query_content = getContentManager().extractContent(query);
				if (content instanceof Result) {
					Result result = (Result) content;					
					// get the original task from the query
					List tasks = (List)result.getValue();
					Task t = (Task) tasks.get(0);						
					Evaluation ev = t.getResult();	
					t.setFinish(getDateTime());
					results.add(t);
					
					// send evaluation to search agent
					ACLMessage reply = query.createReply();
					//Ondrej: musi byt INFORM, ale co kdyz selze vypocet!???
					reply.setPerformative(ACLMessage.INFORM);
					
					Result reply_result = new Result((Action) query_content, ev.getEvaluations());
					getContentManager().fillContent(reply, reply_result);
					
					send(reply);													
				}			
			} catch (UngroundedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			// send new CFP:
			boolean next_query_processes = ProcessNextQuery();
			if (!next_query_processes){				
				number_of_current_tasks--;
			}
		}
	} // end of call for proposal bahavior
	

	protected ACLMessage createCFPmsg(ACLMessage query) {
		ACLMessage cfp = null;
		
		ContentElement content;
		try {
			content = getContentManager().extractContent(query);
							
			ExecuteParameters ep = (ExecuteParameters) (((Action) content).getAction());
			
			// there is only one solution at the time
			Options opt = fillOptionsWithSolution(Options, (SearchSolution)(ep.getSolutions().get(0)));
		
			// create CFP message					  		
			cfp = new ACLMessage(ACLMessage.CFP);
			cfp.setLanguage(codec.getName());
			cfp.setOntology(ontology.getName());

			for (int i = 0; i < computing_agents.size(); ++i) {
				cfp.addReceiver((AID)computing_agents.get(i));
			}
			
			cfp.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			// We want to receive a reply in 10 secs
			cfp.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			Execute ex = new Execute();
			
			// add task id
			Id id = new Id(Integer.toString(task_number));
			Id rtid = received_task.getId();
			rtid.setSubid(id);
			received_task.setId(rtid);
			
			received_task.setStart(getDateTime());
			received_task.setNote(Integer.toString(task_number));
			task_number++;
			
			// add the new options to the task
			pikater.ontology.messages.Agent ag = received_task.getAgent();							
			ag.setOptions(opt.getList());							
			received_task.setAgent(ag);
			ex.setTask(received_task);		
										
			Action a = new Action();
			a.setAction(ex);
			a.setActor(this.getAID());
										
			getContentManager().fillContent(cfp, a);
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return cfp;
	}
	
	
	private boolean ProcessNextQuery(){
				
		if (computing_agents != null){
			if (number_of_current_tasks < computing_agents.size()
					&& query_queue.size() > 0){
				
				ACLMessage query = (ACLMessage)query_queue.get(0);
				query_queue.remove(0);
	
				ACLMessage cfp = createCFPmsg(query);																			
				
				// create new contract net protocol
				addBehaviour(new ExecuteTask(this, cfp, query));	
				return true;
			}
		}
		return false;
	
	} // end ProcessNextQuery
	
	protected class RequestServer extends CyclicBehaviour {

		private static final long serialVersionUID = 1902726126096385876L;

		private MessageTemplate CFPproposalMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP),
				MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),
				MessageTemplate.MatchOntology(ontology.getName()))));

		private MessageTemplate CFPreqMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
				MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),
				MessageTemplate.MatchOntology(ontology.getName()))));
		
		private MessageTemplate queryMsgTemplate = MessageTemplate
				.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_QUERY),
						MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF),
								MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),
										MessageTemplate.MatchOntology(ontology.getName()))));

		private MessageTemplate agreeMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
				MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.AGREE),
				MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),
				MessageTemplate.MatchOntology(ontology.getName()))));
		
		public RequestServer(Agent agent) {			
			super(agent);
		}
		
		@Override 
		public void action() {
			
			ACLMessage CFPreq = receive(CFPreqMsgTemplate);
			ACLMessage CFPproposal = receive(CFPproposalMsgTemplate);			
			ACLMessage query = receive(queryMsgTemplate);
			ACLMessage agree = receive(agreeMsgTemplate);
			boolean msg_received = false;			

			ContentElement content;
			try {				
				if (CFPreq != null){
					msg_received = true;
					content = getContentManager().extractContent(CFPreq);				
					if (((Action) content).getAction() instanceof Execute) {
						received_request = CFPreq;
												
						Execute execute = (Execute) (((Action) content).getAction());
						received_task = execute.getTask();
						Options = received_task.getAgent().getOptions(); 
						
						List mutableOptions = getMutableOptions(Options);
						
						if (mutableOptions.size() > 0){							
							// create search agent												
							ACLMessage msg_ca = new ACLMessage(ACLMessage.REQUEST);
							msg_ca.addReceiver(new AID("agentManager", false));
							msg_ca.setLanguage(codec.getName());
							msg_ca.setOntology(ontology.getName());
							CreateAgent ca = new CreateAgent();
							ca.setType(execute.getMethod().getType());
													
							Action a = new Action();
							a.setAction(ca);
							a.setActor(myAgent.getAID());
									
							String search_agent_name = null;
							try {
								getContentManager().fillContent(msg_ca, a);	
								ACLMessage msg_name = FIPAService.doFipaRequestClient(myAgent, msg_ca);
								search_agent_name = msg_name.getContent();
							} catch (FIPAException e) {
								System.err.println("Exception while adding agent"
										+execute.getMethod().getType()+": " + e);		
							} catch (CodecException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OntologyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// send request to the search agent							
							ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
							msg.addReceiver(new AID(search_agent_name, false));
							msg.setLanguage(codec.getName());
							msg.setOntology(ontology.getName());
							msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
	
							GetParameters gp = new GetParameters();
							List schema = convertOptionsToSchema(received_task.getAgent().getOptions());
							gp.setSchema(schema);
							//gp.setOptions(getMutableOptions(computation.getAgent().getOptions()));						
							gp.setSearch_options(execute.getMethod().getOptions());
							
							a = new Action();
							a.setAction(gp);
							a.setActor(myAgent.getAID());
									
							getContentManager().fillContent(msg, a);	
	
							addBehaviour(new StartGettingParameters(myAgent, msg));
						}
						else{
							// TODO - no ? in options - this shouldn't happen;
							// options without ? should be sent directly to 
							// computing agents by manager
							/* use_search_agent = false;
							Options options = new Options();
							options.setList(task.getAgent().getOptions());
							List l = new ArrayList();
							l.add(options);
							executeTasks(l);
							*/							
						}
						return;
					}
				}
				
				if (CFPproposal != null){
					msg_received = true;
					content = getContentManager().extractContent(CFPproposal);
					if (((Action) content).getAction() instanceof Execute) {
						msg_received = true;
						
						ACLMessage propose = CFPproposal.createReply();
						propose.setPerformative(ACLMessage.PROPOSE);
						propose.setContent(Integer.toString(0)); // TODO
						send(propose);						
						return;
					}
				}
				
				if (query != null) {
					msg_received = true;
					// check whether the query is correct
					content = getContentManager().extractContent(query);					
					if (((Action) content).getAction() instanceof ExecuteParameters) {					
						// options manager received options to execute					
						
						query_queue.add(query);
						ProcessNextQuery();
					}
				}

				if (agree != null) {
					msg_received = true;
					// get max number of tasks
					System.out.println("agree received");
					max_number_of_tasks = Integer.parseInt(agree.getContent());						
					
					// if the agent name is not filled in
					// TODO task.agent - it can be a list
					if (received_task.getAgent().getName() == null){
						// ask agent manager for computing agents
						ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
						req.setLanguage(codec.getName());
						req.setOntology(ontology.getName());
						req.addReceiver(received_request.getSender());
						req.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
						
						pikater.ontology.messages.Agent ag = new pikater.ontology.messages.Agent();
					    ag.setType(received_task.getAgent().getType());
					    
						GetAgents ga = new GetAgents();					    					    
					    ga.setAgent(ag);
					    ga.setNumber(max_number_of_tasks);
					    ga.setTask_id(received_task.getId());
					    
						Action a = new Action();
						a.setAction(ga);
						a.setActor(myAgent.getAID());
			
						try {
							getContentManager().fillContent(req, a);
							ACLMessage reply = FIPAService.doFipaRequestClient(myAgent, req);
							
							ContentElement content1 = getContentManager().extractContent(reply);
							if (content1 instanceof Result) {
								Result result = (Result) content1;
								// get computing agents list
								computing_agents = (List)result.getValue();	// list of AIDs							
							}

							// start processing the queries
							ProcessNextQuery();
							
						} catch (FIPAException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}																									
				}
			} catch (CodecException ce) {
				ce.printStackTrace();
			} catch (OntologyException oe) {
				oe.printStackTrace();
			}
			
			if (! msg_received){
				block();
			}
			
		/* TODO:
		 	ACLMessage result_msg = request.createReply();
		 	result_msg.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			send(result_msg);
			return;
		 */

		}
	}
	

	private class StartGettingParameters extends AchieveREInitiator {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2796507853769993352L;
		
		public StartGettingParameters(Agent a, ACLMessage msg) {
			super(a, msg);
			System.out.println(a.getLocalName()
					+ ": StartGettingParameters behavior created.");
		}

		
		protected void handleInform(ACLMessage inform) {
			System.out.println(getLocalName() + ": Agent "
					+ inform.getSender().getName() + ": sending of Options have been finished.");
			// sending of Options have been finished -> send message to Manager
			sendResultsToManager();			
		}
				
		protected void handleRefuse(ACLMessage refuse) {
			System.out.println(getLocalName() + ": Agent "
					+ refuse.getSender().getName()
					+ " refused to perform the requested action");
			// + preposlat zpravu managerovi
		}

		protected void handleFailure(ACLMessage failure) {
			System.out.println(getLocalName() + ": Agent "
					+ failure.getSender().getName()
					+ ": failure while performing the requested action");
			// preposlat zpravu managerovi
		}

	};
	
	protected boolean registerWithDF() {
		// register with the DF

		DFAgentDescription description = new DFAgentDescription();
		// the description is the root description for each agent
		// and how we prefer to communicate.

		description.setName(getAID());
		// the service description describes a particular service we
		// provide.
		ServiceDescription servicedesc = new ServiceDescription();
		// the name of the service provided (we just re-use our agent name)
		servicedesc.setName(getLocalName());

		// The service type should be a unique string associated with
		// the service.s
		String typeDesc = getAgentType();

		servicedesc.setType(typeDesc);

		// the service has a list of supported languages, ontologies
		// and protocols for this service.
		// servicedesc.addLanguages(language.getName());
		// servicedesc.addOntologies(ontology.getName());
		// servicedesc.addProtocols(InteractionProtocol.FIPA_REQUEST);

		description.addServices(servicedesc);

		// add "OptionsManager agent service"
		ServiceDescription servicedesc_g = new ServiceDescription();

		servicedesc_g.setName(getLocalName());
		servicedesc_g.setType("OptionsManager");
		description.addServices(servicedesc_g);

		// register synchronously registers us with the DF, we may
		// prefer to do this asynchronously using a behaviour.
		try {
			DFService.register(this, description);
			System.out.println(getLocalName()
					+ ": successfully registered with DF; service type: "
					+ typeDesc);
			return true;
		} catch (FIPAException e) {
			System.err.println(getLocalName()
					+ ": error registering with DF, exiting:" + e);
			// doDelete();
			return false;

		}
	} // end registerWithDF

	@Override
	protected void setup() {
		System.out.println(getLocalName() + " is alive...");

		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		registerWithDF();
				
		addBehaviour(new RequestServer(this));

	} // end setup
	
	
	private void sendResultsToManager(){

		ACLMessage msgOut = received_request.createReply();
		msgOut.setPerformative(ACLMessage.INFORM);
		
		// prepare the outgoing message content:
				
		ContentElement content;
			try {
				content = getContentManager().extractContent(received_request);
				Result result = new Result((Action) content, results);
				getContentManager().fillContent(msgOut, result);
				
				send(msgOut);
				
			} catch (UngroundedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}

	
	private List getMutableOptions(List Options){
		List mutable = new ArrayList();
		Iterator itr = Options.iterator();
		while (itr.hasNext()) {
			Option o = (Option) itr.next();
			if (o.getMutable()){				
				mutable.add(o);
			}
		}
		return mutable;
	}
		
	//Create new options from solution with filled ? values (convert solution->options) 
	private Options fillOptionsWithSolution(List options, SearchSolution solution){
		Options res_options = new Options();
		List options_list = new ArrayList();
		if(options==null){
			return res_options;
		}
		//if no solution values to fill - return the option
		if(solution.getValues() == null){
			res_options.setList(options);
			return res_options;
		}
		Iterator sol_itr = solution.getValues().iterator();
		Iterator opt_itr = options.iterator();
		while (opt_itr.hasNext()) {
			Option opt = (Option) opt_itr.next();
			Option new_opt = opt.copyOption();
			if(opt.getMutable())
				new_opt.setValue(fillOptWithSolution(opt, sol_itr));
			options_list.add(new_opt);
		}
		res_options.setList(options_list);
		return res_options;
	}

	//Fill an option's ? with values in iterator
	private String fillOptWithSolution(Option opt, Iterator solution_itr){
		String res_values = "";
		String[] values = ((String)opt.getUser_value()).split(",");
		int numArgs = values.length;
		for (int i = 0; i < numArgs; i++) {
			if (values[i].equals("?")) {
				res_values+=(String)solution_itr.next();
			}else{
				res_values+=values[i];
			}
		}
		return res_values;
	}
	
	//Create schema of solutions from options (Convert options->schema)
	private List convertOptionsToSchema(List options){
		List new_schema = new ArrayList();
		if(options==null)
			return new_schema;
		Iterator itr = options.iterator();
		while (itr.hasNext()) {
			Option opt = (Option) itr.next();
			if(opt.getMutable())
				addOptionToSchema(opt, new_schema);
		}
		return new_schema;
	}
	
	private void addOptionToSchema(Option opt, List schema){
		String[] values = ((String)opt.getUser_value()).split(",");
		int numArgs = values.length;
		if (!opt.getIs_a_set()) {
			if (opt.getData_type().equals("INT") || opt.getData_type().equals("MIXED")) {
				for (int i = 0; i < numArgs; i++) {
					if (values[i].equals("?")) {
						IntSItem itm = new IntSItem();
						itm.setNumber_of_values_to_try(opt.getNumber_of_values_to_try());
						itm.setMin(opt.getRange().getMin().intValue());
						itm.setMax(opt.getRange().getMax().intValue());
						schema.add(itm);
					}
				}
			}else if (opt.getData_type().equals("FLOAT")) {
				for (int i = 0; i < numArgs; i++) {
					if (values[i].equals("?")) {
						FloatSItem itm = new FloatSItem();
						itm.setNumber_of_values_to_try(opt.getNumber_of_values_to_try());
						itm.setMin(opt.getRange().getMin());
						itm.setMax(opt.getRange().getMax());
						schema.add(itm);
					}
				}
			}else if (opt.getData_type().equals("BOOLEAN")) {
				BoolSItem itm = new BoolSItem();
				itm.setNumber_of_values_to_try(opt.getNumber_of_values_to_try());
				schema.add(itm);
			}
		}else{
			for (int i = 0; i < numArgs; i++) {
				if (values[i].equals("?")) {
					SetSItem itm = new SetSItem();
					itm.setNumber_of_values_to_try(opt.getNumber_of_values_to_try());
					itm.setSet(opt.getSet());
					schema.add(itm);
				}
			}
		}
	}

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = new Date();
        return dateFormat.format(date);
    }    

}