/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MIG.ka.bprocess.Gamestates;

import MIG.ka.bprocess.Common.CommonEvent;
import MIG.ka.bprocess.Common.ViewProperty;
import MIG.ka.bprocess.Login.LoginEvent;
import MIG.ka.bprocess.Login.LoginListener;
import MIG.ka.bprocess.Login.Screen.LoginView;
import MIG.ka.bprocess.MainMenu.Screen.MainMenuView;
import MIG.ka.bprocess.MainMenu.StartMultiplayerEvent;
import MIG.ka.bprocess.MainMenu.StartMultiplayerListener;
import MIG.ka.bprocess.MultiplayerGame.Game;
import MIG.ka.bprocess.MultiplayerLobby.Screen.LobbyView;
import MIG.ka.bprocess.Networking.NetworkingClient;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thor
 */
public class GS_Stopped extends Gamestate implements LoginListener, StartMultiplayerListener{

    private LoginView loginScreen;
    private LobbyView lobbyScreen;
    private MainMenuView mainMenuScreen;
    
    public GS_Stopped(LoginView view) {
       super("GS_Stopped");
        this.loginScreen = view;
        this.mainMenuScreen = new MainMenuView();
        this.lobbyScreen = new LobbyView();
        ViewProperty.getInstance().setMainMenuScreen(mainMenuScreen);
        ViewProperty.getInstance().setLobbyScreen(lobbyScreen);
    }

    @Override
    public void actionPerformed(LoginEvent event) 
    {
        boolean LoginSucces;
        LoginSucces = NetworkingClient.getInstance().LoginRequest(event.getName(), event.getPassword());
        if(LoginSucces)
        {
            Logger.getLogger(GS_Stopped.class.getName()).log(Level.INFO , "Username and Password accepted");
            this.mainMenuScreen.initView();
            this.mainMenuScreen.setListener((StartMultiplayerListener)this);
        }
        else
        {
            Logger.getLogger(GS_Stopped.class.getName()).log(Level.INFO , "Wrong Username or Password insert");
            //TODO: Mathode aufrufen, die diesen Fall behandelt
        }
    }   

    /**
     *
     * @param event
     * This Method is not used in this Application.
     * It can be used to Handle all Type of possible Events.
     */
    @Override
    public void actionPerformed(CommonEvent event) 
    {
        System.out.println("test");
    }
    public GS_Stopped() 
    {
        super("GS_Stopped");
    }

    @Override
    public void init() 
    {
        
        this.startLogin();
    }

    @Override
    public void cleanup() {
    }
    
    public void startLogin() {
        ViewProperty.getInstance().setPauseOnLostFocus(false);
	ViewProperty.getInstance().start();
    }

    @Override
    public void actionPerformed(StartMultiplayerEvent event) 
    {
        ViewProperty.getInstance().gotoScreen("lobby");//Lobby View anzeigen
        ArrayList<Game> games = NetworkingClient.getInstance().getAllGames();//Datenmodel abfrage, welche spiele vorhanden sind!
        //Daten in View schreiben!!!
    }
}
