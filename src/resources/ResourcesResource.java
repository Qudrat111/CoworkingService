package resources;

import Model.ApiResponse;
import Model.MeetingRoom;
import Model.WorkPlace;
import Service.ResourceService;

import java.sql.SQLException;
import java.util.Map;

public class ResourcesResource {
    private ResourceService resourceService = new ResourceService();

    public ResourcesResource() throws SQLException {
    }

    public ApiResponse addWorkplace(String id, String location) throws SQLException {
        boolean b = resourceService.addWorkplace(id, location);
        if (b){
            return new ApiResponse("Added workpalce succesfully",200,b);
        }
        return new ApiResponse("Not added workpalce succesfully",404,false);
    }

    public ApiResponse addMeetingRoom(String id, String name) throws SQLException {
        boolean b = resourceService.addMeetingRoom(id, name);
        if (b){
            return new ApiResponse("Added meeting room succesfully",200,b);
        }
        return new ApiResponse("Not added meeting room succesfully",404,false);
    }

    public ApiResponse getAllWorkplaces() {
        Map<String, WorkPlace> allWorkplaces = resourceService.getAllWorkplaces();
        if (allWorkplaces.isEmpty()){
            return new ApiResponse("No workplaces found",404,allWorkplaces);
        }
        return new ApiResponse("All workplaces found",200,allWorkplaces);
    }

    public ApiResponse getAllMeetingRooms() {
        Map<String, MeetingRoom> allMeetingRooms = resourceService.getAllMeetingRooms();
        if (allMeetingRooms.isEmpty()){
            return new ApiResponse("No rooms found",404,allMeetingRooms);
        }
        return new ApiResponse("All rooms found",200,allMeetingRooms);
    }
}
