import React, {Component} from "react";
import {Modal, Button, Row, Col, Form} from 'react-bootstrap';
import {IP_OverComponent} from './IP_OverComponent';
import axios from "axios";

export class ModalComponent extends Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return(
          <Modal
            {...this.props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <Modal.Header closeButton>
              <Modal.Title id="contained-modal-title-vcenter">
                The <b>{this.props.data}</b> plane is over the Iberian Peninsula
              </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="container">
                    Escrever aqui toda a informação do avião.   
                </div>
        
            </Modal.Body>
            <Modal.Footer>
              <Button variant="info" onClick={this.props.onHide}>Close</Button>
            </Modal.Footer>
          </Modal>
        );
    }
}
