import React, { Component } from "react";
import { Row, Col } from "react-bootstrap";
import Aux from "../../hoc/_Aux";
import Card from "../../App/components/MainCard";

class SamplePage extends Component {
  render() {
    return (
      <Aux>
        <Row>
          <Col>
            <img
              src={require("../../assets/images/fst.jpg")}
              alt=""
              style={{ width: "100%", height: "300px" }}
            />
          </Col>
        </Row>
        <Card title="Management of Car Rental " isOption>
          <Col>
            <ul>
              <li> Clients management</li>
              <li> Cars management</li>
              <li> Rental management</li>
            </ul>
          </Col>
        </Card>
      </Aux>
    );
  }
}

export default SamplePage;
