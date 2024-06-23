package Service;

import Model.MeetingRoom;
import Model.WorkPlace;

import java.util.HashMap;
import java.util.Map;

public class ResourceService {
    private Map<String, WorkPlace> workplaces = new HashMap<>();
    private Map<String, MeetingRoom> meetingRooms = new HashMap<>();

    public void addWorkplace(String id, String location) {
        workplaces.put(id, new WorkPlace(id, location));
    }

    public void addMeetingRoom(String id, String name) {
        meetingRooms.put(id, new MeetingRoom(id, name));
    }

    public Map<String, WorkPlace> getAllWorkplaces() {
        return workplaces;
    }

    public Map<String, MeetingRoom> getAllMeetingRooms() {
        return meetingRooms;
    }
}