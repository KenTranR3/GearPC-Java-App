/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThreadBus {
    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }

    public void mutilCastSend(String message){ //like sockets.emit in socket.io
        for(ServerThread serverThread : DashboardController.serverThreadBus.getListServerThreads()){
            try {
                serverThread.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void boardCast(String id, String message){
        for(ServerThread serverThread : DashboardController.serverThreadBus.getListServerThreads()){
            if (serverThread.getClientNumber().equals(id)) {
                continue;
            } else {
                try {
                    serverThread.write(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public int getLength(){
        return listServerThreads.size();
    }

    public void sendOnlineList(){
        String res = "";
        List<ServerThread> threadbus = DashboardController.serverThreadBus.getListServerThreads();
        for(ServerThread serverThread : threadbus){
            res+=serverThread.getClientNumber()+"-";
        }
        DashboardController.serverThreadBus.mutilCastSend("update-online-list"+","+res);
    }
    public void sendMessageToPersion(String id, String message){
        for(ServerThread serverThread : DashboardController.serverThreadBus.getListServerThreads()){
            if(serverThread.getClientNumber().equals(id)){
                try {
                    serverThread.write("global-message"+","+message);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void remove(String id){
        for(int i=0; i<DashboardController.serverThreadBus.getLength(); i++){
            if(DashboardController.serverThreadBus.getListServerThreads().get(i).getClientNumber().equals(id)){
                DashboardController.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }
}
