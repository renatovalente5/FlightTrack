import React from "react";
import PlaneService from "../services/PlaneService";
import ReactDOM from "react-dom";
//import {Button} from 'react-bootstrap';
//import ButtonToolbar from 'react-bootstrap/Button';
import {ButtonToolbar} from 'react-bootstrap';
import {ModalComponent} from './ModalComponent';

class IP_OverComponent extends React.Component {

    constructor(props){
      super(props);
        this.state = {
          planes:[],
          addModalShow: false,
          dados: "oleee"
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 10000);
    }

    async loadData() {
        try {
            PlaneService.getPlanesIpOver().then((response) => {
                this.setState({
                planes: response.data
            })
        });
        } catch (e) {
            console.log(e);
        }
    }
    
      buttonOnClick(planeIdentifier) {
        window.alert(planeIdentifier);
        //console.log("Button clicked = " + planeIdentifier);
        window.open(`/child?id=${planeIdentifier}`, "_blank");
      }
      
            
    render(){       
      let addModalClose = () => this.setState({addModalShow:false});
      const {dados} = this.state;
      
      return(
        <div>
          <h3 className="text-center"> All planes over the Iberian Peninsula </h3>
          <table className = "table table-striped">
            <thead>
              <tr>
                <td>Icao24</td>
                <td>Callsign</td>
                <td>Origin Country</td>
                <td>Time position</td>
                <td>Longitude</td>
                <td>Latitude</td>
                <td>Altitude</td>
                <td>Velocity</td>
                <td>View</td>
              </tr>
            </thead>
              <tbody>
                {
                  this.state.planes.map(
                    plane =>
                    <tr key = {plane.icao}>
                      <td>{plane.icao}</td>
                      <td>{plane.callsign}</td>
                      <td>{plane.origin_country}</td>
                      <td>{plane.time_position}</td>
                      <td>{plane.longitude}</td>
                      <td>{plane.latitude}</td>
                      <td>{plane.baro_altitude}</td>
                      <td>{plane.velocity}</td>
                      <td> 
                      <ButtonToolbar>
                        <button variant ="danger" onClick={() => this.buttonOnClick(plane.icao)}>
                            View
                        </button>
                       
                      </ButtonToolbar> 
                        </td>
                    </tr>
                  )
                }
              </tbody>
          </table>
          
        </div>
      );
    }
}

export default IP_OverComponent


