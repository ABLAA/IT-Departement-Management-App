import React, { Component } from "react";
import {
  Row,
  Col,
  Card,
  Table,
  Button,
  Modal,
  Form,
  Alert
} from "react-bootstrap";
import {
  ValidationForm,
  TextInput,
  FileInput
} from "react-bootstrap4-form-validation";
import Aux from "../../hoc/_Aux";
import axios from "axios";
import validator from "validator";
import * as moment from "moment";

class Clients extends Component {
  state = {
    isVertically: false,
    isVertically1: false,
    clients: [],
    nom: "",
    prenom: "",
    numeroCIN: "",
    numPermis: "",
    dateNaissance: "",
    adresse: "",
    chkBasic: false,
    chkCustom: false,
    checkMeSwitch: false,
    showModal: false
  };
  componentDidMount() {
    this.getClients();
    console.log("ok");
  }
  getClients = () => {
    axios
      .get("http://localhost:3000/clients")
      .then(response => {
        this.setState({ clients: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  handleCheckboxChange = (e, value) => {
    this.setState({
      [e.target.name]: value
    });
  };

  handleChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    });
  };

  handleSubmit = (e, formData, inputs) => {
    e.preventDefault();
    //alert(JSON.stringify(formData, null, 2));
    this.setState({ showModal: true });
  };

  handleErrorSubmit = (e, formData, errorInputs) => {
    //console.log(errorInputs);
  };

  matchPassword = value => {
    return value && value === this.state.password;
  };
  deleteclient = code => {
    let clients = this.state.clients.filter(client => client._id !== code);
    this.setState({ clients });
    axios
      .delete(`http://localhost:3000/clients/${code}`)
      .then(response => {
        // this.setState({ Clients: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  addclient = () => {
    let clients = this.state.clients;
    let client = {
      numeroCIN: this.state.numeroCIN,
      numPermis: this.state.numPermis,
      nom: this.state.nom,
      prenom: this.state.prenom,
      dateNaissance: this.state.dateNaissance,
      adresse: this.state.adresse
    };
    axios
      .post("http://localhost:3000/clients", client)
      .then(function(response) {
        console.log(response.data);
        if (response.data.message.length) {
          clients.push(response.data);
          this.setState({ clients });
        } else alert("check information");
      })
      .catch(function(error) {
        alert("check information");
      });
  };
  editclient = client => {
    let oldClients = this.state.clients;
    let upadetClient = {
      numeroCIN: !this.state.numeroCIN.length
        ? client.numeroCIN
        : this.state.numeroCIN,
      numPermis: !this.state.numPermis.length
        ? client.numPermis
        : this.state.numPermis,
      nom: !this.state.nom.length ? client.nom : this.state.nom,
      prenom: !this.state.prenom.length ? client.prenom : this.state.prenom,
      dateNaissance: !this.state.dateNaissance.length
        ? client.dateNaissance
        : this.state.dateNaissance,
      adresse: !this.state.adresse.length ? client.adresse : this.state.adresse
    };
    let clients = oldClients.map(item =>
      item._id === client._id ? upadetClient : item
    );
    this.setState({ clients });

    axios
      .put(`http://localhost:3000/clients/${client._id}`, upadetClient)
      .then(function(response) {
        console.log(response.data);
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  render() {
    return (
      <Aux>
        <Row>
          <Col>
            <Card>
              <Card.Header>
                <Card.Title>
                  <Row>
                    <Col md={11}>
                      <h5>List of Clients</h5>
                    </Col>
                    <Col md={1}>
                      <Button
                        className="btn-icon btn"
                        variant="secondary"
                        onClick={() => this.setState({ isVertically1: true })}
                      >
                        <i className="feather icon-plus-square" />
                      </Button>
                      <Modal
                        centered
                        show={this.state.isVertically1}
                        onHide={() => this.setState({ isVertically1: false })}
                      >
                        <Modal.Header closeButton>
                          <Modal.Title as="h5">Add client</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                          <ValidationForm
                            onSubmit={this.handleSubmit}
                            onErrorSubmit={this.handleErrorSubmit}
                          >
                            <Form.Row>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nom">
                                  First name
                                </Form.Label>
                                <TextInput
                                  name="nom"
                                  id="nom"
                                  placeholder="First Name"
                                  required
                                  value={this.state.nom}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                  minLength="3"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="prenom">
                                  Last name
                                </Form.Label>
                                <TextInput
                                  minLength="3"
                                  name="prenom"
                                  id="prenom"
                                  placeholder="Last Name"
                                  value={this.state.prenom}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nom">
                                  numero CIN
                                </Form.Label>
                                <TextInput
                                  name="numeroCIN"
                                  id="numeroCIN"
                                  placeholder="numero CIN"
                                  required
                                  value={this.state.numeroCIN}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                  minLength="3"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nom">
                                  Num Permis
                                </Form.Label>
                                <TextInput
                                  name="numPermis"
                                  id="numPermis"
                                  placeholder="Num Permis"
                                  required
                                  value={this.state.numPermis}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                  minLength="3"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="dateNaissance">
                                  Birth date
                                </Form.Label>
                                <TextInput
                                  name="dateNaissance"
                                  id="dateNaissance"
                                  placeholder="birth Date"
                                  value={this.state.dateNaissance}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                  pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"
                                  errorMessage={{
                                    pattern:
                                      "Please enter a valid date : yyyy-mm-dd"
                                  }}
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="departement">
                                  Adresse
                                </Form.Label>
                                <TextInput
                                  name="adresse"
                                  id="adresse"
                                  placeholder="Adresse"
                                  required
                                  errorMessage={{
                                    required: "departement is required",
                                    pattern: "departement is invalid."
                                  }}
                                  value={this.state.adresse}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="upload_avatar">
                                  Upload Avatar
                                </Form.Label>
                                <div className="custom-file">
                                  <FileInput
                                    name="avatar"
                                    id="avatar"
                                    fileType={["png", "jpg", "jpeg"]}
                                    maxFileSize="100 kb"
                                    errorMessage={{
                                      required: "Please upload a file",
                                      fileType:
                                        "Only .png or .jpg file is allowed",
                                      maxFileSize: "Max file size is 100 kb"
                                    }}
                                  />
                                </div>
                              </Form.Group>
                              <Form.Group as={Col} sm={12} className="mt-3">
                                <Button
                                  onClick={() => {
                                    this.addclient();
                                  }}
                                  type="submit"
                                >
                                  add client
                                </Button>
                              </Form.Group>
                            </Form.Row>
                          </ValidationForm>
                        </Modal.Body>
                        <Modal.Footer>
                          <Button
                            variant="secondary"
                            onClick={() =>
                              this.setState({ isVertically1: false })
                            }
                          >
                            Close
                          </Button>
                        </Modal.Footer>
                      </Modal>
                    </Col>
                  </Row>
                </Card.Title>
              </Card.Header>
              <Card.Body>
                <Table responsive>
                  <thead>
                    <tr>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Num Cin</th>
                      <th>Num Permis</th>
                      <th>birth Date</th>
                      <th>Adresse</th>
                      <th>delete</th>
                      <th>update</th>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.clients.map(client => (
                      <tr key={client._id}>
                        <td>{client.nom}</td>
                        <td>{client.prenom}</td>
                        <td>{client.numPermis}</td>
                        <td>{client.numeroCIN}</td>
                        <td>
                          {moment(client.dateNaissance).format("YYYY/MM/DD")}
                        </td>
                        <td>{client.adresse}</td>
                        <td>
                          <Button
                            className="btn-icon btn"
                            variant="danger"
                            onClick={() => this.deleteclient(client._id)}
                          >
                            <i className="feather icon-trash" />
                          </Button>
                        </td>
                        <td>
                          <Button
                            className="btn-icon btn"
                            variant="secondary"
                            onClick={() =>
                              this.setState({ isVertically: true })
                            }
                          >
                            <i className="feather icon-edit" />
                          </Button>
                          <Modal
                            centered
                            show={this.state.isVertically}
                            onHide={() =>
                              this.setState({ isVertically: false })
                            }
                          >
                            <Modal.Header closeButton>
                              <Modal.Title as="h5">edit client</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                              <ValidationForm
                                onSubmit={this.handleSubmit}
                                onErrorSubmit={this.handleErrorSubmit}
                              >
                                <Form.Row>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="nom">
                                      First name
                                    </Form.Label>
                                    <TextInput
                                      name="nom"
                                      id="nom"
                                      placeholder="First Name"
                                      required
                                      value={this.state.nom}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="prenom">
                                      Last name
                                    </Form.Label>
                                    <TextInput
                                      name="prenom"
                                      id="prenom"
                                      placeholder="Last Name"
                                      value={this.state.prenom}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="prenom">
                                      numero CIN
                                    </Form.Label>
                                    <TextInput
                                      name="numeroCIN"
                                      id="numeroCIN"
                                      placeholder="numero CIN"
                                      value={this.state.numeroCIN}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="prenom">
                                      num Permis
                                    </Form.Label>
                                    <TextInput
                                      name="numPermis"
                                      id="numPermis"
                                      placeholder="numero CIN"
                                      value={this.state.numPermis}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>

                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="dateNaissance">
                                      Birth date
                                    </Form.Label>
                                    <TextInput
                                      name="dateNaissance"
                                      id="dateNaissance"
                                      placeholder="birth Date"
                                      value={this.state.dateNaissance}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="departement">
                                      Adresse
                                    </Form.Label>
                                    <TextInput
                                      name="adresse"
                                      id="adresse"
                                      placeholder="client Class "
                                      required
                                      errorMessage={{
                                        required: "departement is required",
                                        pattern: "departement is invalid."
                                      }}
                                      value={this.state.adresse}
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>

                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="upload_avatar">
                                      Upload Avatar
                                    </Form.Label>
                                    <div className="custom-file">
                                      <FileInput
                                        name="avatar"
                                        id="avatar"
                                        fileType={["png", "jpg", "jpeg"]}
                                        maxFileSize="100 kb"
                                        errorMessage={{
                                          required: "Please upload a file",
                                          fileType:
                                            "Only .png or .jpg file is allowed",
                                          maxFileSize: "Max file size is 100 kb"
                                        }}
                                      />
                                    </div>
                                  </Form.Group>
                                  <Form.Group as={Col} sm={12} className="mt-3">
                                    <Button
                                      onClick={() => {
                                        this.editclient(client);
                                      }}
                                      type="submit"
                                    >
                                      edit client
                                    </Button>
                                  </Form.Group>
                                </Form.Row>
                              </ValidationForm>
                            </Modal.Body>
                            <Modal.Footer>
                              <Button
                                variant="secondary"
                                onClick={() =>
                                  this.setState({ isVertically: false })
                                }
                              >
                                Close
                              </Button>
                            </Modal.Footer>
                          </Modal>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Aux>
    );
  }
}

export default Clients;
