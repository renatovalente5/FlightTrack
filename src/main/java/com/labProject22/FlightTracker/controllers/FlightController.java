package com.labProject22.FlightTracker.controllers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@RestController
//@RequestMapping("/myapp")
@CrossOrigin("*")
@EnableScheduling
public class FlightController {
       
    private RestTemplate restTemplate;
    private List<Plane> overPeninsula = new LinkedList<Plane>();
    private List<Plane> entrouNaPeninsula = new LinkedList<Plane>();
    private List<Plane> saiuDaPeninsula = new LinkedList<Plane>();
    private Map<String, LinkedList<Plane>> trackerPlane = new HashMap<String, LinkedList<Plane>>();
    private static final Logger logger = LogManager.getLogger(FlightController.class);

    private String url = "https://opensky-network.org/api/states/all?";
    
    public FlightController(){
        this.restTemplate = new RestTemplate();
    }
        
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
        
    @GetMapping("/in")
    @Scheduled(fixedRate = 30000L)
    public List<Plane> getOutPlanes_IberianPeninsula(){
        overPeninsula = getPlanes("lamin=36.7&lomin=-8.23&lamax=42&lomax=-2.7");
        return null;
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
    @Scheduled(fixedRate = 30000L)
    public List<Plane> getAllPlanes_IberianPeninsula(){
        //entrouNaPeninsula.clear();
        //saiuDaPeninsula.clear();
        List<Plane> aux = overPeninsula;
        
        overPeninsula = getPlanes("lamin=36.7&lomin=-8.23&lamax=42&lomax=-2.7");
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
                
    
    @GetMapping(path = "/event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> "Flux - " + LocalTime.now().toString());
    }
        
    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> ServerSentEvent.<String> builder()
            .id(String.valueOf(sequence))
              .event("periodic-event")
              .data("SSE - " + LocalTime.now().toString())
              .build());
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
