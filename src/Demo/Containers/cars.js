import React, { Component } from "react";
import {
  Row,
  Col,
  Card,
  Table,
  Button,
  Modal,
  Form,
  ListGroup,
  ListGroupItem
} from "react-bootstrap";
import {
  Container,
  ButtonDropdown,
  DropdownMenu,
  DropdownItem,
  DropdownToggle
} from "reactstrap";

import {
  ValidationForm,
  TextInput,
  BaseFormControl,
  FileInput
} from "react-bootstrap4-form-validation";
import MaskedInput from "react-text-mask";
import Aux from "../../hoc/_Aux";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import * as moment from "moment";
import validator from "validator";

class MaskWithValidation extends BaseFormControl {
  constructor(props) {
    super(props);
    this.inputRef = React.createRef();
  }

  getInputRef() {
    return this.inputRef.current.inputElement;
  }

  handleChange = e => {
    this.checkError();
    if (this.props.onChange) this.props.onChange(e);
  };

  render() {
    return (
      <React.Fragment>
        <MaskedInput
          ref={this.inputRef}
          {...this.filterProps()}
          onChange={this.handleChange}
        />
        {this.displayErrorMessage()}
        {this.displaySuccessMessage()}
      </React.Fragment>
    );
  }
}
class Cars extends Component {
  constructor(props) {
    super(props);
    this.toggle1 = this.toggle1.bind(this);
    this.state = {
      model: false,
      dropdownOpen1: false,
      modeleList: ["A9", "A7", "Q5", "Q8"],
      MarqueList: ["AUDI", "FIAT", "BMW", "JEEP", "FORD", "KIA"],
      isVertically: false,
      isVertically1: false,
      cars: [],
      carsClasses: [],
      numImmatriculation: "",
      numChasis: "",
      datePMC: "",
      marque: "",
      modele: "",
      prixLocation: "",
      puissanceFiscal: "",
      assurreur: "",
      cotisation: "",
      type: "",
      imgURL: "",
      chkBasic: false,
      chkCustom: false,
      checkMeSwitch: false,
      showModal: false
    };
    this.handleChange3 = this.handleChange3.bind(this);
  }
  toggle1() {
    this.setState({
      dropdownOpen1: !this.state.dropdownOpen1
    });
  }
  handleCheckboxChange = (e, value) => {
    this.setState({
      [e.target.name]: value
    });
  };
  componentDidMount() {
    this.getcars();
  }
  getcars = () => {
    axios
      .get("http://localhost:3000/voitures")
      .then(response => {
        this.setState({ cars: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  getcarsClasses = () => {
    axios.get("http://localhost:8080/StudentClasss").then(response => {
      this.setState({ carsClasses: response.data });
      console.log(this.state.carsClasses);
      console.log("ok");
    });
  };

  handleChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    });
  };
  handleChange2(e) {
    console.log(e.target.value);
    this.setState({ modele: e.target.value });
  }
  handleChangeStatus(e) {
    console.log(e.target.value);
    this.setState({ marque: e.target.value });
  }
  handleChange3 = date => {
    let data = new Date(date);
    const datePMC = moment(data).format("YYYY-mm-DD");
    this.setState({ datePMC });
    console.log(datePMC);
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
  deleteCar = code => {
    let cars = this.state.cars.filter(car => car._id !== code);
    this.setState({ cars });
    axios
      .delete(`http://localhost:3000/voitures/${code}`)
      .then(response => {
        // this.setState({ cars: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  addCar = () => {
    let cars = this.state.cars;
    let car = {
      numChasis: this.state.numChasis,
      numImmatriculation: this.state.numImmatriculation,
      marque: this.state.marque,
      modele: this.state.modele,
      datePMC: this.state.datePMC,
      prixLocation: this.state.prixLocation,
      assurence: {
        assurreur: this.state.assurreur,
        type: this.state.type,
        cotisation: this.state.cotisation
      },
      imgURL: this.state.imgURL.substring(
        this.state.imgURL.lastIndexOf("\\") + 1
      )
    };
    console.log(car);
    axios
      .post("http://localhost:3000/voitures", car)
      .then(function(response) {
        console.log(response.data);
        cars.push(response.data);
        this.setState({ cars });
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  editStudent = code => {
    let cars = this.state.cars.map(student =>
      student.code == code
        ? {
            code: Math.random(),
            firstName: this.state.firstName.length
              ? this.state.firstName
              : student.firstName,
            numChasis: this.state.numChasis.length
              ? this.state.numChasis
              : student.numChasis,
            datePMC: this.state.datePMC.length
              ? this.state.datePMC
              : student.datePMC,
            status: this.state.status.length
              ? this.state.status
              : student.status
          }
        : student
    );
    // cars = cars.map(student => {
    //   if (student.code === code) {
    //     return {
    //       code: Math.random(),
    //       firstName: this.state.firstName,
    //       numChasis: this.state.numChasis,
    //       datePMC: this.state.datePMC,
    //       status: this.state.status
    //     };
    //   }
    // });
    console.log(cars, this.state.firstName);
    this.setState({ cars });
  };
  getPickerValue = value => {
    console.log(value);
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
                    <Col md={9}>
                      <h5>List of cars</h5>
                    </Col>
                    <Col md={2}>
                      {" "}
                      <ButtonDropdown
                        direction="down"
                        style={{ marginBottom: "10px", marginRight: "5px" }}
                        isOpen={this.state.dropdownOpen1}
                        toggle={this.toggle1}
                      >
                        <DropdownToggle caret color="warning">
                          Select Marque
                        </DropdownToggle>
                        <DropdownMenu>
                          {this.state.MarqueList.map(model => (
                            <DropdownItem key={model} onClick={this.select1}>
                              {model}
                            </DropdownItem>
                          ))}
                        </DropdownMenu>
                      </ButtonDropdown>
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
                          <Modal.Title as="h5">Add Car</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                          <ValidationForm
                            onSubmit={this.handleSubmit}
                            onErrorSubmit={this.handleErrorSubmit}
                          >
                            <Form.Row>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="numImmatriculation">
                                  Num immatricule
                                </Form.Label>
                                <TextInput
                                  name="numImmatriculation"
                                  id="numImmatriculation"
                                  placeholder="num Immatriculation"
                                  required
                                  value={this.state.numImmatriculation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="numChasis">
                                  num Chasis
                                </Form.Label>
                                <TextInput
                                  name="numChasis"
                                  id="numChasis"
                                  placeholder="num Chasis"
                                  value={this.state.numChasis}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="puissanceFiscal">
                                  prix Location
                                </Form.Label>
                                <TextInput
                                  name="prixLocation"
                                  id="prixLocation"
                                  placeholder="prix Location "
                                  value={this.state.prixLocation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="datePMC">
                                  Date PMC
                                </Form.Label>
                                <TextInput
                                  name="datePMC"
                                  id="datePMC"
                                  placeholder="Year-Month-Day"
                                  value={this.state.datePMC}
                                  onChange={this.handleChange}
                                  pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"
                                  errorMessage={{
                                    pattern:
                                      "Please enter a valid date : yyyy-mm-dd"
                                  }}
                                  autoComplete="off"
                                />
                                <DatePicker
                                  showIcon={false}
                                  placeholderText="  click to choose date"
                                  value={this.state.datePMC}
                                  selected={new Date()}
                                  onChange={this.handleChange3}
                                  dateFormat="d MMM yyyy"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="assurreur">
                                  assurreur
                                </Form.Label>
                                <TextInput
                                  name="assurreur"
                                  id="assurreur"
                                  placeholder="assurreur"
                                  value={this.state.assurreur}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nombreCilyndre">
                                  cotisation
                                </Form.Label>
                                <TextInput
                                  name="cotisation"
                                  id="cotisation"
                                  placeholder="cotisation"
                                  value={this.state.cotisation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nombreCilyndre">
                                  type assurence
                                </Form.Label>
                                <TextInput
                                  name="type"
                                  id="type"
                                  placeholder="type"
                                  value={this.state.type}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="upload_avatar">
                                  Upload Picture
                                </Form.Label>
                                <div className="custom-file">
                                  <FileInput
                                    name="imgURL"
                                    id="imgURL"
                                    fileType={["png", "jpg", "jpeg"]}
                                    onChange={this.handleChange}
                                    maxFileSize="1000 kb"
                                    errorMessage={{
                                      required: "Please upload a file",
                                      fileType:
                                        "Only .png or .jpg file is allowed",
                                      maxFileSize: "Max file size is 100 kb"
                                    }}
                                  />
                                </div>
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">Marque</Form.Label>
                                <select
                                  style={{ padding: "10px", margin: "11px" }}
                                  onChange={e => this.handleChangeStatus(e)}
                                >
                                  <option value="BMW">BMW </option>
                                  <option value="AUDI">AUDI </option>
                                  <option value="PORCHE">PORCHE </option>
                                  <option value="FORD">FORD </option>
                                  <option value="JEEP">JEEP </option>
                                  <option value="KIA">KIA </option>
                                  <option value="FIAT">FIAT</option>
                                </select>
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">Modele</Form.Label>

                                <select
                                  style={{ padding: "10px", margin: "10px" }}
                                  onChange={e => this.handleChange2(e)}
                                >
                                  {this.state.modeleList.map(classe => (
                                    <option key={classe} value={classe}>
                                      {classe}
                                    </option>
                                  ))}
                                </select>
                              </Form.Group>

                              <Form.Group as={Col} sm={12} className="mt-3">
                                <Button
                                  onClick={() => {
                                    this.addCar();
                                  }}
                                  type="submit"
                                >
                                  add Car
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
            </Card>
            <Row>
              {this.state.cars.map(car => (
                <Col md={4} key={car._id}>
                  <Card key={car._id}>
                    <Card.Img
                      variant="top"
                      src={require(`../../assets/images/${car.imgURL}`)}
                      onClick={() => this.setState({ model: true })}
                    />
                    <Card.Body>
                      <Card.Title>{car.marque + " " + car.modele}</Card.Title>
                      <ListGroup className="list-group-flush">
                        <ListGroupItem>
                          num Chasis : {car.numChasis}
                        </ListGroupItem>
                        <ListGroupItem>
                          num immatricule : {car.numImmatriculation}
                        </ListGroupItem>
                      </ListGroup>
                      <br></br>
                      <Button
                        className="btn-icon btn"
                        variant="danger"
                        onClick={() => this.deleteCar(car._id)}
                      >
                        <i className="feather icon-trash" />
                      </Button>
                      <Button
                        className="btn-icon btn"
                        variant="secondary"
                        onClick={() => this.setState({ isVertically: true })}
                      >
                        <i className="feather icon-edit" />
                      </Button>
                      <Modal
                        centered
                        show={this.state.isVertically}
                        onHide={() => this.setState({ isVertically: false })}
                      >
                        <Modal.Header closeButton>
                          <Modal.Title as="h5">Edit Car</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                          <ValidationForm
                            onSubmit={this.handleSubmit}
                            onErrorSubmit={this.handleErrorSubmit}
                          >
                            <Form.Row>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="numImmatriculation">
                                  Num immatricule
                                </Form.Label>
                                <TextInput
                                  name="numImmatriculation"
                                  id="numImmatriculation"
                                  placeholder="num Immatriculation"
                                  required
                                  value={this.state.numImmatriculation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="numChasis">
                                  num Chasis
                                </Form.Label>
                                <TextInput
                                  name="numChasis"
                                  id="numChasis"
                                  placeholder="num Chasis"
                                  value={this.state.numChasis}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="puissanceFiscal">
                                  prix Location
                                </Form.Label>
                                <TextInput
                                  name="prixLocation"
                                  id="prixLocation"
                                  placeholder="prix Location "
                                  value={this.state.prixLocation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="datePMC">
                                  Date PMC
                                </Form.Label>
                                <TextInput
                                  name="datePMC"
                                  id="datePMC"
                                  placeholder="Year-Month-Day"
                                  value={this.state.datePMC}
                                  onChange={this.handleChange}
                                  pattern="([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"
                                  errorMessage={{
                                    pattern:
                                      "Please enter a valid date : yyyy-mm-dd"
                                  }}
                                  autoComplete="off"
                                />
                                <DatePicker
                                  showIcon={false}
                                  placeholderText="  click to choose date"
                                  value={this.state.datePMC}
                                  selected={new Date()}
                                  onChange={this.handleChange3}
                                  dateFormat="d MMM yyyy"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="assurreur">
                                  assurreur
                                </Form.Label>
                                <TextInput
                                  name="assurreur"
                                  id="assurreur"
                                  placeholder="assurreur"
                                  value={this.state.assurreur}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nombreCilyndre">
                                  cotisation
                                </Form.Label>
                                <TextInput
                                  name="cotisation"
                                  id="cotisation"
                                  placeholder="cotisation"
                                  value={this.state.cotisation}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="nombreCilyndre">
                                  type assurence
                                </Form.Label>
                                <TextInput
                                  name="type"
                                  id="type"
                                  placeholder="type"
                                  value={this.state.type}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="upload_avatar">
                                  Upload Picture
                                </Form.Label>
                                <div className="custom-file">
                                  <FileInput
                                    name="imgURL"
                                    id="imgURL"
                                    fileType={["png", "jpg", "jpeg"]}
                                    onChange={this.handleChange}
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

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">Marque</Form.Label>
                                <select
                                  style={{ padding: "10px", margin: "11px" }}
                                  onChange={e => this.handleChangeStatus(e)}
                                >
                                  <option value="BMW">BMW </option>
                                  <option value="AUDI">AUDI </option>
                                  <option value="PORCHE">PORCHE </option>
                                  <option value="FORD">FORD </option>
                                  <option value="JEEP">JEEP </option>
                                  <option value="KIA">KIA </option>
                                  <option value="FIAT">FIAT</option>
                                </select>
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">Modele</Form.Label>

                                <select
                                  style={{ padding: "10px", margin: "10px" }}
                                  onChange={e => this.handleChange2(e)}
                                >
                                  {this.state.modeleList.map(classe => (
                                    <option key={classe} value={classe}>
                                      {classe}
                                    </option>
                                  ))}
                                </select>
                              </Form.Group>

                              <Form.Group as={Col} sm={12} className="mt-3">
                                <Button
                                  onClick={() => {
                                    this.addCar();
                                  }}
                                  type="submit"
                                >
                                  add Car
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
                    </Card.Body>
                  </Card>

                  <Modal
                    size="lg"
                    centered
                    aria-labelledby="contained-modal-title-vcenter"
                    show={this.state.model}
                    onHide={() => this.setState({ model: false })}
                  >
                    <Modal.Header closeButton>
                      <Modal.Title id="contained-modal-title-vcenter">
                        {car.marque + " " + car.modele}
                      </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <Row>
                        <Col md={6}>
                          <img
                            variant="top"
                            style={{ width: "100%" }}
                            src={require(`../../assets/images/${car.imgURL}`)}
                            onClick={() => this.setState({ model: true })}
                          />
                        </Col>
                        <Col md={6}>
                          <h4>
                            <b>num immat : </b> {car.numImmatriculation}
                          </h4>
                          <h4>
                            <b>num Chasis : </b>
                            {car.numChasis}
                          </h4>
                          <h4>
                            <b>Date mise Circulation : </b>
                            {moment(car.datePMC).format("YYYY/MM/DD")}
                          </h4>
                          <h4>
                            <b>price :</b> {car.prixLocation} $
                          </h4>
                          <h4>
                            <b>puissanceFiscal :</b> {car.puissanceFiscal}
                          </h4>
                          <h4>
                            <b>nombreCilyndre :</b>
                            {car.nombreCylindre}
                          </h4>
                          <h4>
                            <b> assurence : </b>

                            <ul>
                              <li>assurreur : {car.assurence.assurreur}</li>
                              <li>type assurence : {car.assurence.type}</li>
                              <li>cotisation {car.assurence.cotisation}</li>
                            </ul>
                          </h4>
                        </Col>
                      </Row>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button onClick={() => this.setState({ model: false })}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
                </Col>
              ))}
            </Row>
          </Col>
        </Row>
      </Aux>
    );
  }
}

export default Cars;
