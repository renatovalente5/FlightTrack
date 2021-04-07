import React from "react";
import Alert from 'react-bootstrap/Alert';
import axios from "axios";

class Palha extends React.Component {

    constructor(props){
      super(props)
        this.state = {
          ddd:""
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
    }

    async postData() {
        try {
            var newURL = "http://localhost:8081/"+"4952a4";
            return axios.post(newURL);
            axios.post().then(response => {
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
          <h3 className="text-center"> Test </h3>

           <Alert variant="warning">
            <Alert.Heading>ALERT !</Alert.Heading>
            <hr />
            <p className="mb-0"> Acabou de entrar na Península Ibérica o avião <b> XPTO </b> com país de origem XPTO2, latitude XPTO3 e longitude XPTO4. </p>  
          </Alert>

          {this.state.ddd}
        </div>
      )
    }
}

export default Palha


