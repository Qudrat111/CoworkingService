package Service;

import Model.MeetingRoom;
import Model.WorkPlace;
import repository.ResourceRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ResourceService {
    private ResourceRepository resourceRepository = new ResourceRepository();

    public ResourceService() throws SQLException {
    }

    public boolean addWorkplace(String id, String location) throws SQLException {
        return resourceRepository.addWorkplace(id, location);
    }

    public boolean addMeetingRoom(String id, String name) throws SQLException {
       return resourceRepository.addMeetingRoom(id, name);
    }

    public Map<String, WorkPlace> getAllWorkplaces() {
        return resourceRepository.getAllWorkplaces();
    }

    public Map<String, MeetingRoom> getAllMeetingRooms() {
        return resourceRepository.getAllMeetingRooms();
    }
}