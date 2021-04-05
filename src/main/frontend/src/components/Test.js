import React from "react";
import PlaneService from "../services/PlaneService";

class Test extends React.Component {

    constructor(props){
      super(props)
        this.state = {
          ddd:""
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
//      setInterval(this.loadData, 10000);
    }

    async loadData() {
        try {
            PlaneService.getTest().then((response) => {
                this.setState({
                ddd: response.data
            })
        });
        } catch (e) {
            console.log(e);
        }
    }
          
    render(){
      return(
        <div>
          <h3 className="text-center"> Test </h3>

          {this.state.ddd}
        </div>
      )
    }
}

export default Test


