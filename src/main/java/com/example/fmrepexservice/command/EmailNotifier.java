package com.example.fmrepexservice.command;

import com.example.fmrepexservice.announcementmanagement.business.Announcement;
import com.example.fmrepexservice.authentication.Login;
import com.example.fmrepexservice.authentication.Registration;
import com.example.fmrepexservice.authentication.UserManagement;
import com.example.fmrepexservice.logger.Log;
import com.example.fmrepexservice.logger.LogStore;

public class EmailNotifier extends CommandObserver{
    private LogStore logStore;
    public EmailNotifier(Command command){
        super(command);
        logStore = LogStore.getInstance();
    }
    @Override
    public void doUpdate(Command command) {
        if(command.isSuccessful()){
            if(command instanceof UserCommand){
                UserCommand userCommand = (UserCommand) command;
                userMessage(userCommand);
            } else if(command instanceof RequestCommand){
                RequestCommand requestCommand = (RequestCommand) command;
                requestMessage(requestCommand);
            } else if(command instanceof WorkOrderCommand){
                WorkOrderCommand workOrderCommand = (WorkOrderCommand) command;
                workOrderMessage(workOrderCommand);
            } else if(command instanceof BuildingCommand){
                BuildingCommand buildingCommand = (BuildingCommand) command;
                buildingMessage(buildingCommand);
            }else if(command instanceof AnnouncementCommand){
                AnnouncementCommand announcementCommand = (AnnouncementCommand) command;
                announcementMessage(announcementCommand);
            }
        }
    }

    public void userMessage(UserCommand command){
        if(command.isApproved()){
            logStore.addToLog(new Log("User Approved"));
            displayMessage(command.userToRegister.getEmail() + " approved successfully.");
        }
        if(command.added){
            logStore.addToLog(new Log("User Added"));
        System.out.printf("%s(%s) registered by %s(%s).\n",
                command.userToRegister.getFullName(),
                command.userToRegister.getEmail(),
                command.loggedInUser.getFullName(),
                command.loggedInUser.getEmail()
        );
        }
    }
    public void buildingMessage(BuildingCommand command){
        if(command.buildingAssigned){
            logStore.addToLog(new Log("Building Assigned"));
            displayMessage("Building assigned successfully");
        }
        if(command.buildingCreated){
            logStore.addToLog(new Log("Building Created"));
            displayMessage("Building created successfully");
        }
        if(command.buildingEdited){
            logStore.addToLog(new Log("Building Edited"));
            displayMessage("Building edited successfully");
        }
        if(command.buildingDeAssigned){
            logStore.addToLog(new Log("Building De-Assigned"));
            displayMessage("Building de assigned successfully");
        }
    }

    public void workOrderMessage(WorkOrderCommand command){
        if(command.isWorkOrderCreated()){
            logStore.addToLog(new Log("Work Order Created"));
            displayMessage("Work order created and assigned");
        }
        if(command.isRequestActivated()){
            logStore.addToLog(new Log("Request Activated"));
            displayMessage("Work order accepted");
        }
        if(command.isRequestCompleted()){
            logStore.addToLog(new Log("Request Completed"));
            displayMessage("Work order completed");
        }
    }

    public void requestMessage(RequestCommand command){
        if(command.isRequestCreated()){
            logStore.addToLog(new Log("Request Created"));
            System.out.printf("%s(%s) created by %s(%s).\n",
                    command.request.getAsset(),
                    command.request.getCategory(),
                    command.loggedInUser.getFullName(),
                    command.loggedInUser.getEmail()
            );
        }
        if (command.isRequestEdited()){
            logStore.addToLog(new Log("Request Edited"));
            System.out.printf("%s(%s) signed off by %s(%s).\n",
                    command.request.getAsset(),
                    command.request.getCategory(),
                    command.loggedInUser.getFullName(),
                    command.loggedInUser.getEmail()
            );
        }
    }

    public void announcementMessage(AnnouncementCommand command){
        Announcement announcement = command.announcement;
        if(command.announcementCreated){
            logStore.addToLog(new Log("Announcement Created"));
        }
        if(command.announcementEdited){
            logStore.addToLog(new Log("Announcement Edited"));
        }
    }

    private void displayMessage(String m){
        System.out.println(m);
    }
}
