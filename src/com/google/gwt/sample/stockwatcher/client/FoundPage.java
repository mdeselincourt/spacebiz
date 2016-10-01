package com.google.gwt.sample.stockwatcher.client;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.gwt.sample.stockwatcher.client.services.BusinessService;
import com.google.gwt.sample.stockwatcher.client.services.BusinessServiceAsync;

import com.google.gwt.sample.stockwatcher.client.services.PlayerService;
import com.google.gwt.sample.stockwatcher.client.services.PlayerServiceAsync;

import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.util.log.Log;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.objectify.Key;
import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.sample.stockwatcher.client.data.cards.CharacterClass;

/**
 * Create-persistent-Player form. 
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * TODO: Post-CCG design, "Found" is the wrong name for this page, it needs to be renamed or maybe multi-purposed.
 */
public class FoundPage extends Content {

	final Logger logger;
	
	//// Declare services
	
	// Declare business Service async
	BusinessServiceAsync businessServiceAsync;
	PlayerServiceAsync playerServiceAsync;
	
	// Declare widgets
	VerticalPanel pageRootPanel;

	// Player form
	Label newPlayerHandleLabel;
	TextBox newPlayerHandleField;
	
	// // Player/CCG form
	ListBox characterClassesListBox;
	
	// Biz form
	TextBox nameField;
	TextBox tickerField;
	Label companyIdentityEntryLabel;
	Label foundingLoanLabel;
	TextBox foundingLoanField;
	Label foundingLoanOutcomeLabel;
	Label foundingEquityLabel;
	TextBox foundingEquityField;	
	Label foundingEquityOutcomeLabel;
	Label foundingCapitalLabel;
	
	
	// Form bottom
	Button foundCompanyButton;
	Button marketPageButton;
	
	/**
	 * Constructor - draw the page
	 */
	public FoundPage() {

		logger = Logger.getLogger("SpaceBiz");
	
		instantiateWidgets(); // Instantiate widgets

		assemblePage();
		
		// Very specific-function custom Handler for experimenting...
		class CapitalInputHandler implements KeyUpHandler {

			/**
			 * Fired when the user finishes hitting Enter
			 */
			public void onKeyUp(KeyUpEvent event) {
				// Previously: if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)

				int loan;
				try {
					loan = (Integer.parseInt(foundingLoanField.getText()));
				}
				catch (NumberFormatException e)
				{
					loan = 0;
				}

				double interest = loan * 0.098;
				String lt = "Select a loan up to £5000:";

				// Client-side validation for user convenience
				if (loan > 5000) {
					lt = "The lender will not loan you more than £5000:";
					foundingLoanField.setText("5000");
					loan = 5000;
					interest = loan * 0.098;
				}
				else if (loan < 0) {
					foundingLoanField.setText("0");
					loan = 0;
					interest = 0;
				}

				foundingLoanLabel.setText(lt);
				foundingLoanOutcomeLabel.setText("£" + interest + " interest per annum");

				// EQUITY

				int equityPc; 
				try {
					equityPc = (Integer.parseInt(foundingEquityField.getText()));
				}
				catch (NumberFormatException e)
				{
					equityPc = 0;
				}
				int equity = equityPc * 100;
				String et = "Select an equity issue % up to 49:";

				// Client-side validation for user convenience
				if (equityPc > 49) {
					et = "You cannot issue more than 49% equity without losing control of the company";
					foundingEquityField.setText("49");
					equityPc = 49;
					equity = equityPc * 100;
				}
				else if (equityPc < 0) {
					foundingEquityField.setText("0");
					equityPc = 0;
					equity = 0;						
				}

				foundingEquityLabel.setText(et);
				foundingEquityOutcomeLabel.setText("£" + equity + " raised by selling " + equityPc + " of 100 shares");

				foundingCapitalLabel.setText("£" + (equity + loan) + " total capital raised");
			}
		}
		
		// Another handler
		class FoundButtonHandler implements ClickHandler {
			
			public void onClick(ClickEvent event) {
				logger.log(Level.INFO, "Found button handler firing");
				
				////
				// Post the player
				////
				
				// 1. Initialise the service proxy
				
				if (playerServiceAsync == null) {
					playerServiceAsync = GWT.create(PlayerService.class);
				}
				
				// 2. Set up the callback object (GWT standard, but uses generics for my stuff). I don't recall why <String>
				//    This object will process what happens when we get a reply from the RPC
				
				/** 
				 * ANONYMOUS INNER CLASS THAT EXECUTES ASYNCHRONOUSLY UPON RESPONSE 
				 * 
				 * TODO: Actually UPDATE THE UI UPON RESPONSE! 
				 */
				AsyncCallback<Key<Player>> playerCallback = new AsyncCallback<Key<Player>>() {
					
					// 2.a() react to failures
					public void onFailure(Throwable exception) {
						logger.log(Level.SEVERE, "Player service called back onFailure()!");
						exception.printStackTrace();
					}
					
					// 2.b() react to success
					public void onSuccess(Key<Player> newPlayerKey) {
						logger.log(Level.INFO, "Player service called back onSuccess()!");
						// logger.log(Level.INFO, "Response from service was :" + outcome);
					}
				}; // End of inline AsyncCallback
				
				// 3. Collect the data and call the service
				
				Player newPlayer = new Player();
				
				// We DON'T set the ID because the server can do that from the current user's gae email address, ensuring it's correct
				newPlayer.setHandle(newPlayerHandleField.getText());
				newPlayer.setActiveBusiness(null); // TODO: This will need to be implemented or come out
				
				// TODO: Store the player's choice of class
				
				// TODO: Catch serialisation exceptions
				// Do not bother to specify GAE user data- the service needs to be
				// the code that does that because that is only accessible to the
				// server.
				playerServiceAsync.putPlayer(newPlayer, newPlayerHandleLabel.getText(), playerCallback);			
				
				// End of service call
				
				////
				// Post the business TODO: As of 7-7-16 this is structured correctly but I don't believe the service is complete
				////
				
				// 1. Initialise the service proxy
				if (businessServiceAsync == null) {
					businessServiceAsync = GWT.create(BusinessService.class);
				}
				
				// 2. Set up the callback object (GWT standard, but uses generics for my stuff)
				
				AsyncCallback<String> businessCallback = new AsyncCallback<String>() {

					// 2.a() react to failures
					public void onFailure(Throwable exception) {
						logger.log(Level.SEVERE, "Found service called back onFailure()!");
						exception.printStackTrace();
					}

					// 2.b() react to success
					public void onSuccess(String outcome) {
						logger.log(Level.INFO, "Found service called back onSuccess()!");
						logger.log(Level.INFO, "Response from service was :" + outcome);
					}
				}; // End of inline AsyncCallback

				// 3. Call the service
				businessServiceAsync.postBusiness(new Business(), businessCallback);
				
				
				
			} // End of onClick
			
		} // End of FoundButtonHandler
		
		// INSTANTIATE AND LINK UP HANDLERS
		
		// Found button
		
		FoundButtonHandler foundButtonHandler = new FoundButtonHandler();
		foundCompanyButton.addClickHandler(foundButtonHandler);

		// Create a handler to handle loan box changes and attach it to the fields
		CapitalInputHandler capitalInputHandler = new CapitalInputHandler();
		foundingLoanField.addKeyUpHandler(capitalInputHandler);
		foundingEquityField.addKeyUpHandler(capitalInputHandler);

		// Render the page
		initWidget(pageRootPanel);

	} // end of Go() (to build the UI)

private void instantiateWidgets() {
		
		pageRootPanel = new VerticalPanel();
		//// Company Identity
		
		// Field for new player (but hide it)
		newPlayerHandleLabel = new Label();
		newPlayerHandleLabel.setText("Choose a new player handle:");
		
		newPlayerHandleField = new TextBox();
		
		hideNewPlayer();
		
		// Field for char type selection
		characterClassesListBox = new ListBox();
		
		ArrayList<CharacterClass> ccal = CharacterClass.getCharacterClassData(); 
		
		Iterator<CharacterClass> ccalIterator = ccal.iterator();
		
		while(ccalIterator.hasNext()) {
			CharacterClass cc = ccalIterator.next();
			characterClassesListBox.addItem(cc.getName(), cc.getCharacterClassId());
		}

		
		// field for accepting company name
		nameField = new TextBox();
		nameField.setText("Acme");

		// field for accepting ticker code
		tickerField = new TextBox();
		tickerField.setText("ACM");

		// label for introducing / client-erroring company name and ticker
		companyIdentityEntryLabel = new Label();
		companyIdentityEntryLabel.setText("Please enter your company name and ticker code:");

		//// Finance 

		// Loans
		foundingLoanLabel = new Label("Select a loan up to £5000:");
		foundingLoanField = new TextBox();
		foundingLoanField.setText("0");		
		foundingLoanOutcomeLabel = new Label("£0 interest per annum");

		// Equity
		foundingEquityLabel = new Label("Select an equity % of up to 49:");
		foundingEquityField = new TextBox();
		foundingEquityField.setText("0");		
		foundingEquityOutcomeLabel = new Label("£0 equity");

		// Capital raised summary
		foundingCapitalLabel = new Label("£-");

		// button for founding company
		foundCompanyButton = new Button("Found");
		foundCompanyButton.addStyleName("sendButton");

		// button for switching to market view
		marketPageButton = new Button("Market Page");

		// End of Instantiating widgets
		
	}
	
	private void assemblePage() {
		
		pageRootPanel.add(newPlayerHandleLabel);
		pageRootPanel.add(newPlayerHandleField);
		
		// Player/CCG form
		pageRootPanel.add(characterClassesListBox);
		
		// Founding form
		pageRootPanel.add(companyIdentityEntryLabel);
		pageRootPanel.add(nameField);
		pageRootPanel.add(tickerField);

		pageRootPanel.add(foundingLoanLabel);
		pageRootPanel.add(foundingLoanField);
		pageRootPanel.add(foundingLoanOutcomeLabel);

		pageRootPanel.add(foundingEquityLabel);
		pageRootPanel.add(foundingEquityField);
		pageRootPanel.add(foundingEquityOutcomeLabel);

		pageRootPanel.add(foundingCapitalLabel);

		pageRootPanel.add(foundCompanyButton);
		pageRootPanel.add(marketPageButton);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		
	}

	

	public void showNewPlayer() { 
		
		logger.log(Level.INFO, "Showing the new player form by updating stylenames");
		
		newPlayerHandleLabel.removeStyleName("hidden");
		newPlayerHandleLabel.addStyleName("unhidden");
		
		newPlayerHandleField.removeStyleName("hidden");
		newPlayerHandleField.addStyleName("unhidden");
		
		// Doesn't work... characterClassesListBox.removeStyleName("hidden");
		//characterClassesListBox.addStyleName("unhidden");
	}
	
	public void hideNewPlayer() {
		
		logger.log(Level.INFO, "Hiding the new player form by updating stylenames");
		
		newPlayerHandleLabel.removeStyleName("unhidden");
		newPlayerHandleLabel.addStyleName("hidden");
		
		newPlayerHandleField.removeStyleName("unhidden");
		newPlayerHandleField.addStyleName("hidden");
		
		// Doesn't work... characterClassesListBox.removeStyleName("hidden");
		// characterClassesListBox.addStyleName("unhidden");
	}
}
