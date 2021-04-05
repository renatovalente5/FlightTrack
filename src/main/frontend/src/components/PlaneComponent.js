import React from "react";
import PlaneService from "../services/PlaneService";

class PlaneComponent extends React.Component {

    constructor(props){
      super(props)
        this.state = {
          planes:[]
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
//      setInterval(this.loadData, 10000);
    }

    async loadData() {
        try {
            PlaneService.getPlanes().then((response) => {
                this.setState({
                planes: response.data
            })
        });
        } catch (e) {
            console.log(e);
        }
    }
          
    render(){
      return(
        <div>
          <h3 className="text-center"> All planes </h3>

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
                    </tr>
                  )
                }
              </tbody>
          </table>
        </div>
      )
    }
}

export default PlaneComponent


