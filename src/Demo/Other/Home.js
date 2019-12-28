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
              style={{ width: "100%" }}
            />
          </Col>
        </Row>
        <Card title="Management of IT department" isOption>
          <Row>
            <Col>
              <ul>
                <li> Student management</li>
                <li> Teachers management</li>
                <li> Administrative management</li>
              </ul>
            </Col>
          </Row>
        </Card>
      </Aux>
    );
  }
}

export default SamplePage;
