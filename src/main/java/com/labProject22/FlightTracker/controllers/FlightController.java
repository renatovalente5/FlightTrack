package com.labProject22.FlightTracker.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
//@RequestMapping("/myapp")
@CrossOrigin("*")
public class FlightController {
       
    private RestTemplate restTemplate;
    private List<Plane> allPlanes = new LinkedList<Plane>();
    private List<Plane> portugalPlanes = new LinkedList<Plane>();
    private List<Plane> spainPlanes = new LinkedList<Plane>();
    private List<Plane> irebianPeninsulaPlanes = new LinkedList<Plane>();
    private List<Plane> overPeninsula = new LinkedList<Plane>();
    private List<Plane> entryPeninsula = new LinkedList<Plane>();
    private Map<String, LinkedList<Plane>> trackerPlane = new HashMap<String, LinkedList<Plane>>();
    private static final Logger logger = LogManager.getLogger(FlightController.class);
    
    public FlightController(){
        this.restTemplate = new RestTemplate();
    }
        
    @GetMapping("/planes")
    public List<Plane> getPlanes(){
        String url = "https://opensky-network.org/api/states/all?";
        System.out.println("ola");
        PlanesResponse objects = restTemplate.getForObject(url, PlanesResponse.class);
        
        allPlanes.clear();
        for (int i=0; i < objects.getStates().length; i++){
            allPlanes.add(addPlane(objects,i));
        } 
        
        //Guardar percuso de todos os aviões a cada 10 seg.
        for (Plane p : allPlanes) {
            LinkedList<Plane> auxList = new LinkedList<Plane>();
            if(trackerPlane.containsKey(p.getIcao())){
                
                auxList = trackerPlane.get(p.getIcao());
                if(p.getIcao() != null){
                    auxList.add(p);
                }
                trackerPlane.put(p.getIcao(), auxList);
            }else{
                if(p.getIcao() != null){
                    auxList.add(p);
                }
                trackerPlane.put(p.getIcao(), auxList);
            }
        }
        
        System.out.println("oli");
        logger.debug("Debug log message");
        for (Map.Entry<String, LinkedList<Plane>> p : trackerPlane.entrySet()) {
            System.out.println(p.getKey() + ":" + p.getValue());
        }
        
        return allPlanes;
    }
    
    @GetMapping("/ip")
//    @GetMapping("/planes/ip")
    public List<Plane> getIrebianPeninsulaOriginPlanes(){    
        irebianPeninsulaPlanes.clear();
        List<Plane> temp = getPlanes();
        for (Plane plane:temp){
            if(plane.getOrigin_country().equals("Spain") || plane.getOrigin_country().equals("Portugal")){
                irebianPeninsulaPlanes.add(plane);
            }
        }   
        
        return irebianPeninsulaPlanes;
        
//        irebianPeninsulaPlanes.clear();
//        for (Plane plane:allPlanes){
//            if(plane.getOrigin_country().equals("Spain") || plane.getOrigin_country().equals("Portugal")){
//                irebianPeninsulaPlanes.add(plane);
//            }
//        }       
//        return irebianPeninsulaPlanes;

    }
    
    @GetMapping("/portugal")
//    @GetMapping("/planes/ip/portugal")
    public List<Plane> getPortugalPlanes(){
        portugalPlanes.clear();
        List<Plane> temp = getPlanes();
        for (Plane plane:temp){
            if(plane.getOrigin_country().equals("Portugal")){
                portugalPlanes.add(plane);
            }
        }   
        
//        portugalPlanes.clear();
//        for (Plane plane:allPlanes){
//            if(plane.getOrigin_country().equals("Portugal")){
//                portugalPlanes.add(plane);
//            }
//        }
        return portugalPlanes;
    }
    
    @GetMapping("/spain")
//    @GetMapping("/planes/ip/spain")
    public List<Plane> getSpainPlanes(){
        spainPlanes.clear();
        
        List<Plane> temp = getPlanes();
        for (Plane plane:temp){
            if(plane.getOrigin_country().equals("Spain")){
                spainPlanes.add(plane);
            }
        }   
        
//        for (Plane plane:allPlanes){
//            if(plane.getOrigin_country().equals("Spain")){
//                spainPlanes.add(plane);
//            }
//        }        
        return spainPlanes;
    }
        
    @GetMapping("/over")
//    @GetMapping("/planes/ip/over")
    public List<Plane> getIrebianPeninsulaPlanes(){  
        overPeninsula.clear();
        
        List<Plane> temp = getPlanes();
        for (Plane plane:temp){
            if(plane.getLongitude() >= -8.23 && plane.getLongitude() <= -2.7 && plane.getLatitude() >= 36.7 && plane.getLatitude() <= 42){
                    overPeninsula.add(plane);
            }
        }   
        
//        for (Plane plane:allPlanes){
//            if(plane.getLongitude() >= -8.23 && plane.getLongitude() <= -2.7 && plane.getLatitude() >= 36.7 && plane.getLatitude() <= 42){
//                    overPeninsula.add(plane);
//            }
//        }
        return overPeninsula;
    }
    
    @GetMapping("/entry")
//    @GetMapping("/planes/ip/entry")
    public List<Plane> getIrebianPeninsulaEntryPlanes(){    
        entryPeninsula.clear();
        List<Plane> temp = getPlanes();
        //List<Plane> over = getIrebianPeninsulaPlanes();
        for (Plane plane:temp){
            if(plane.getLongitude() >= -8.23 && plane.getLongitude() <= -2.7 && plane.getLatitude() >= 36.7 && plane.getLatitude() <= 42){
                if(!overPeninsula.contains(plane)){
                    entryPeninsula.add(plane);
                }
            }
        }   
        
//        for (Plane plane:allPlanes){
//            if(plane.getLongitude() >= -8.23 && plane.getLongitude() <= -2.7 && plane.getLatitude() >= 36.7 && plane.getLatitude() <= 42){
//                if(!overPeninsula.contains(plane)){
//                    entryPeninsula.add(plane);
//                }
//            }
//        }
        return entryPeninsula;
    }
    
//    @GetMapping("/child?id=")
//    public void getChild(){
//        
//    }
      
    private Plane addPlane(PlanesResponse p, int i){
        String icao = null;
        String callsign = null;
        String origin_country = null;
        int time_position = 0;
        int last_contact = 0;
        float longitude = 0;
        float latitude = 0;
        float baro_altitude = 0;
        boolean on_ground = false;
        float velocity = 0;
        float true_track = 0;
        float vertical_rate = 0;
        int[] sensors = null;
        float geo_altitude = 0;
        String squawk = null;
        boolean spi = false;
        int position_source = 0;

        if(p.getStates()[i][0] != null){
            icao = p.getStates()[i][0];
        }
        if(p.getStates()[i][1] != null){
            callsign = p.getStates()[i][1];
        }
        if(p.getStates()[i][2] != null){
            origin_country = p.getStates()[i][2];
        }
        if(p.getStates()[i][3] != null){
            time_position =  Integer.parseInt(p.getStates()[i][3]);
        }
        if(p.getStates()[i][4] != null){
            last_contact = Integer.parseInt(p.getStates()[i][4]);
        }
        if(p.getStates()[i][5] != null){
            longitude = Float.parseFloat(p.getStates()[i][5]);
        }
        if(p.getStates()[i][6] != null){
            latitude = Float.parseFloat(p.getStates()[i][6]);
        }
        if(p.getStates()[i][7] != null){
            baro_altitude = Float.parseFloat(p.getStates()[i][7]);
        }
        if(p.getStates()[i][8] != null){
            on_ground = Boolean.parseBoolean(p.getStates()[i][8]);
        }
        if(p.getStates()[i][9] != null){
            velocity =  Float.parseFloat(p.getStates()[i][9]);
        }
        if(p.getStates()[i][10] != null){
            true_track = Float.parseFloat(p.getStates()[i][10]);
        }
        if(p.getStates()[i][11] != null){
            vertical_rate = Float.parseFloat(p.getStates()[i][11]);
        }
        if(p.getStates()[i][12] != null){
            sensors = null;
        }
        if(p.getStates()[i][13] != null){
            geo_altitude = Float.parseFloat(p.getStates()[i][13]);
        }
        if(p.getStates()[i][14] != null){
            squawk =  p.getStates()[i][14];
        }
        if(p.getStates()[i][15] != null){
            spi = Boolean.parseBoolean(p.getStates()[i][15]);
        }
        if(p.getStates()[i][16] != null){
            position_source = Integer.parseInt(p.getStates()[i][16]);
        }

        Plane pl = new Plane(icao,callsign,origin_country,time_position,last_contact,longitude,latitude,
                baro_altitude,on_ground,velocity,true_track,vertical_rate,sensors,geo_altitude,squawk,spi,position_source);
        return pl;
    }
    
}
