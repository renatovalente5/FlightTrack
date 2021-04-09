import React, { Component, useState } from 'react';
import axios from "axios";
import ReactMapGL, {Marker} from 'react-map-gl';

export default function Test () {
        
    const [viewport, setViewPort] = useState({ 
        latitude: 39.983011, 
        longitude: -4.087557, 
        width: "72vw", 
        height:"52vh",
        zoom: 5
    });
   
    return (
        <div >
            <ReactMapGL {...viewport} 
            mapboxApiAccessToken="pk.eyJ1Ijoicml0YS1hbWFudGU5OTU1IiwiYSI6ImNrbmEyZGpzYzBqcjcybm55Z2NyOTVkazMifQ.oRw17OIsKSA0CeIUG2UC1Q"
            onViewportChange={nextviewport => {
                setViewPort(nextviewport);
            }} >   
                <Marker latitude={40.6405} longitude={8.6538}>
                    <img src="travelling.png" width="20" height="20"/>
                </Marker>
                        
            </ReactMapGL>

        </div>
    );
    
}
