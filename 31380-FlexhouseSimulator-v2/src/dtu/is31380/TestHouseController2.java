package dtu.is31380;

import java.util.ArrayList;
import java.util.Vector;

public class TestHouseController2 extends AbstractHouseController {

  private Vector<ActuatorConfig> room1_heaters;
  private Vector<SensorConfig> room1_temps;
  
  public TestHouseController2() {
    super(5000); //set timestep to 5000ms
  }
  
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();
    if (intf.getSimulationTime()>100) {
      for (ActuatorConfig ac:room1_heaters) {
        if (intf.getActuatorSetpoint(ac.name)<0.5) {
          intf.setActuator(ac.name, 1.0); //switch heater in room 1 on
        }
      }
    }
    double t1=0;
    for (SensorConfig sc:room1_temps) {
      t1+=intf.getSensorValue(sc.name);
    }
    System.out.println("T_room1="+(t1/room1_temps.size()));
    
  }

  @Override
  protected void init() {
    room1_heaters=new Vector<ActuatorConfig>();
    room1_temps=new Vector<SensorConfig>();
    BuildingConfig bc=getInterface().getBuildingConfig();
    ArrayList<RoomConfig> rooms=bc.getRooms();
    for (RoomConfig r:rooms) {
      if (r.name.equals("room1")) {
        ArrayList<ActuatorConfig> actuators=r.getActuators();
        for (ActuatorConfig a:actuators) {
          if (a.type==SystemConstants.DeviceType.PowerControlledHeater) {
            getInterface().setActuator(a.name, 0.0);
            System.out.println("Found actuator "+a.name+". Setting to off.");
            room1_heaters.add(a);
          }
        }
        ArrayList<SensorConfig> sensors=r.getSensors();
        for (SensorConfig s:sensors) {
          if (s.type==SystemConstants.DeviceType.Temperature) {
            System.out.println("Found sensor "+s.name+".");
            room1_temps.add(s);
          }
        }
      }
    }
  }
}
