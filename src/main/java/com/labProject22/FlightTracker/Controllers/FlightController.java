package com.labProject22.FlightTracker.controllers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
//@RequestMapping("/myapp")
@CrossOrigin("*")
@EnableScheduling
public class FlightController {
    
    private final TopicProducer topicProducer; 
    private final PlaneInRepository getInPlaneRepository;
    private final PlaneOutRepository getOutPlanesRepository;
    private RestTemplate restTemplate = new RestTemplate();
    private List<Plane> overPeninsula = new LinkedList<Plane>();
    private List<Plane> entrouNaPeninsula = new LinkedList<Plane>();
    private List<Plane> saiuDaPeninsula = new LinkedList<Plane>();
    private Map<String, LinkedList<Plane>> trackerPlane = new HashMap<String, LinkedList<Plane>>();
    private static final Logger logger = LogManager.getLogger(FlightController.class);

    private String url = "https://opensky-network.org/api/states/all?";

        
    // Obter todos os avioes com certos parametros
    public List<Plane> getPlanes(String parameters){
        PlanesResponse obj = restTemplate.getForObject(url+parameters,PlanesResponse.class);
        List<Plane> planes = new LinkedList<Plane>();
        for(int i = 0; i < obj.getStates().length; i++){
            planes.add(addPlane(obj,i));
        }
        return planes;
    }       
    
    // Obter todos os avioes na area da Peninsula Iberica para mostrar no mapa
    @GetMapping("/map")    
    @Scheduled(fixedRate = 5000, initialDelay = 30000)
    public List<Plane> getIberianPeninsula(){
        return getPlanes("lamin=36.375299&lomin=-9.789897&lamax=42.911378&lomax=2.259536");
    }


    // Obter dados (trajeto) do aviao com o icao=id
    @GetMapping("/{id}")
    public List<Plane> getDataPlane(@PathVariable String id){
        for (Map.Entry<String, LinkedList<Plane>> plane : trackerPlane.entrySet()){
            if (plane.getKey().equals(id)){
                return plane.getValue();
            }
        }
        return null;
    }
    
    // Obter todos os avioes na area da Peninsula Iberica (retorna uma lista sempre atualizada)
    @GetMapping("/over")    
    @Scheduled(fixedRate = 10000L)
    public List<Plane> getAllPlanes_IberianPeninsula(){
        //entrouNaPeninsula.clear();
        //saiuDaPeninsula.clear();

        List<Plane> aux = overPeninsula;
        
        overPeninsula = getPlanes("lamin=36.375299&lomin=-9.789897&lamax=42.911378&lomax=2.259536");
        if(aux.isEmpty()) aux = overPeninsula;
        
        //Para ver se ENTROU algum Avião
        boolean in;
        for(Plane p: overPeninsula){
            in = false;
            String icao = p.getIcao();
            for(Plane a: aux){
                if(a.getIcao().equals(icao)){
                    in = true;
                }
            }
            if(in==false){
                entrouNaPeninsula.add(p);
                System.out.println("--- Entrou ---");
                topicProducer.send("plane", "O avião " + p.getIcao() + " entrou na Peninsula!");
//                TrackerPlane tp = new TrackerPlane(p.getIcao(),p.getOrigin_country(),p.getLongitude(),p.getLatitude());
                PlaneIn pIn = new PlaneIn(p.getIcao(), p.getCallsign(), p.getOrigin_country(), p.getTime_position(),
                        p.getLast_contact(), p.getLongitude(), p.getLatitude(), p.getBaro_altitude(),
                        p.isOn_ground(), p.getVelocity(), p.getTrue_track(), p.getVertical_rate(),
                        p.getSensors(), p.getGeo_altitude(), p.getSquawk(), p.isSpi(), p.getPosition_source());
                getInPlaneRepository.save(pIn);
                System.out.println(".......Salvou na BD........");
            }
        }
        System.out.println("Entrou Lista: " + entrouNaPeninsula);
        if(!entrouNaPeninsula.isEmpty()){
            //Ativar evento de entrada
        }
        
        //Para ver se SAIU algum Avião
        boolean out;
        for(Plane a: aux){
            out = false;
            String icao = a.getIcao();
            for(Plane p: overPeninsula){
                if(p.getIcao().equals(icao)){
                    out = true;
                }
            }
            if(out==false){
                saiuDaPeninsula.add(a);
                System.out.println("--- Saiu ---");
                topicProducer.send("plane", "O avião " + a.getIcao() + " saiu na Peninsula!");
                PlaneOut pOut = new PlaneOut(a.getIcao(), a.getCallsign(), a.getOrigin_country(), a.getTime_position(),
                        a.getLast_contact(), a.getLongitude(), a.getLatitude(), a.getBaro_altitude(),
                        a.isOn_ground(), a.getVelocity(), a.getTrue_track(), a.getVertical_rate(),
                        a.getSensors(), a.getGeo_altitude(), a.getSquawk(), a.isSpi(), a.getPosition_source());
                getOutPlanesRepository.save(pOut);
            }
        }
        System.out.println("Saiu Lista: " + saiuDaPeninsula);
            if(!saiuDaPeninsula.isEmpty()){
                //Ativar evento de saida
        }

        // Guardar trajeto do aviao
        for (Plane p : overPeninsula) {
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
        return overPeninsula;   
    }

    @GetMapping("/in")
    @Scheduled(fixedRate = 10000L)
    public List<PlaneIn> getInPlanesBD(){
        System.out.println("--------------------------------");
//        System.out.println(getInPlaneRepository.findAll());
        System.out.println("--------------------------------");
        return getInPlaneRepository.findAll();
    }

    @GetMapping("/out")
    @Scheduled(fixedRate = 10000L)
    public List<PlaneOut> getOutPlanesBD(){
        System.out.println("--------------------------------");
//        System.out.println(getOutPlanesRepository.findAll());
        System.out.println("--------------------------------");
        return getOutPlanesRepository.findAll();
    }

              
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
