import React, { Component } from "react";
import { Row, Col, Card, Table, Button, Modal, Form } from "react-bootstrap";
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
class Students extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isVertically: false,
      isVertically1: false,
      students: [],
      studentsClasses: [],
      firstName: "",
      lastName: "",
      birthDate: "",
      status: "",
      class: "",
      classCode: "",
      chkBasic: false,
      chkCustom: false,
      checkMeSwitch: false,
      showModal: false
    };
    this.handleChange3 = this.handleChange3.bind(this);
  }

  handleCheckboxChange = (e, value) => {
    this.setState({
      [e.target.name]: value
    });
  };
  componentDidMount() {
    this.getStudents();
    this.getStudentsClasses();
    console.log(this.state.class);
  }
  getStudents = () => {
    axios
      .get("http://localhost:8080/Students")
      .then(response => {
        this.setState({ students: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  getStudentsClasses = () => {
    axios.get("http://localhost:8080/StudentClasss").then(response => {
      this.setState({ studentsClasses: response.data });
      console.log(this.state.studentsClasses);
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
    this.setState({ class: e.target.value });
  }
  handleChangeStatus(e) {
    console.log(e.target.value);
    this.setState({ status: e.target.value });
  }
  handleChange3 = date => {
    let data = new Date(date);
    const birthDate = moment(data).format("YYYY-mm-DD");
    this.setState({ birthDate });
    console.log(birthDate);
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
  deleteStudent = code => {
    let students = this.state.students.filter(student => student.code !== code);
    this.setState({ students });
    axios
      .delete(`http://localhost:8080/Student/${code}`)
      .then(response => {
        // this.setState({ students: response.data });
        console.log(response.data);
        console.log("ok");
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  addStudent = () => {
    let students = this.state.students;
    let studentsClasses = this.state.studentsClasses;
    let elmt = 0;
    for (let i = 0; i < studentsClasses.length; i++) {
      if (studentsClasses[i].code === this.state.class) elmt = i;
    }
    let student = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      birthDate: this.state.birthDate,
      status: this.state.status,
      classCode: this.state.class
    };
    console.log(student);
    axios
      .post("http://localhost:8080/Student", student)
      .then(function(response) {
        console.log(response.data);
        students.push(response.data);
        studentsClasses[elmt].students.push(response.data);
        console.log(studentsClasses);
        this.setState({ studentsClasses });
        this.setState({ students });
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  editStudent = code => {
    let students = this.state.students.map(student =>
      student.code == code
        ? {
            code: Math.random(),
            firstName: this.state.firstName.length
              ? this.state.firstName
              : student.firstName,
            lastName: this.state.lastName.length
              ? this.state.lastName
              : student.lastName,
            birthDate: this.state.birthDate.length
              ? this.state.birthDate
              : student.birthDate,
            status: this.state.status.length
              ? this.state.status
              : student.status
          }
        : student
    );
    // students = students.map(student => {
    //   if (student.code === code) {
    //     return {
    //       code: Math.random(),
    //       firstName: this.state.firstName,
    //       lastName: this.state.lastName,
    //       birthDate: this.state.birthDate,
    //       status: this.state.status
    //     };
    //   }
    // });
    console.log(students, this.state.firstName);
    this.setState({ students });
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
                    <Col md={11}>
                      <h5>List of Students</h5>
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
                          <Modal.Title as="h5">Add student</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                          <ValidationForm
                            onSubmit={this.handleSubmit}
                            onErrorSubmit={this.handleErrorSubmit}
                          >
                            <Form.Row>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="firstName">
                                  First name
                                </Form.Label>
                                <TextInput
                                  name="firstName"
                                  id="firstName"
                                  placeholder="First Name"
                                  required
                                  value={this.state.firstName}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="lastName">
                                  Last name
                                </Form.Label>
                                <TextInput
                                  name="lastName"
                                  id="lastName"
                                  placeholder="Last Name"
                                  value={this.state.lastName}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="birthDate">
                                  Birth date
                                </Form.Label>
                                <TextInput
                                  name="birthDate"
                                  id="birthDate"
                                  placeholder="Year-Month-Day"
                                  value={this.state.birthDate}
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
                                  className="date-picker"
                                  popperClassName="drv-datepicker-popper"
                                  value={this.state.birthDate}
                                  selected={new Date()}
                                  onChange={this.handleChange3}
                                  dateFormat="yyyy-MM-dd"
                                  maxDate={moment().subtract(6570, "days")}
                                  minDate={moment()}
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
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">class</Form.Label>

                                <select
                                  style={{ padding: "10px", margin: "10px" }}
                                  onChange={e => this.handleChange2(e)}
                                >
                                  {this.state.studentsClasses.map(classe => (
                                    <option
                                      key={classe.code}
                                      value={classe.code}
                                    >
                                      {classe.section.name +
                                        "  " +
                                        classe.year.year}
                                    </option>
                                  ))}
                                </select>
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="status">Status</Form.Label>
                                <select
                                  style={{ padding: "10px", margin: "11px" }}
                                  onChange={e => this.handleChangeStatus(e)}
                                >
                                  <option value="PASSED">PASSED </option>
                                  <option value="FAILED">FAILED </option>
                                  <option value="POSTPONED">POSTPONED </option>
                                  <option value="IN_PROGRESS">
                                    IN_PROGRESS
                                  </option>
                                </select>
                              </Form.Group>

                              <Form.Group as={Col} sm={12} className="mt-3">
                                <Button
                                  onClick={() => {
                                    this.addStudent();
                                  }}
                                  type="submit"
                                >
                                  add student
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
                      <th>#</th>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Birth Date</th>
                      <th>Status</th>
                      <th>Section</th>
                      <th>Delete</th>
                      <th>Update</th>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.students.map(student => (
                      <tr key={student.code}>
                        <th scope="row">1</th>
                        <td>{student.firstName}</td>
                        <td>{student.lastName}</td>
                        <td>
                          {moment(student.birthDate).format("YYYY/MM/DD")}
                        </td>
                        <td>{student.status}</td>
                        <td>
                          {this.state.studentsClasses.map(id =>
                            id.students.map(stud =>
                              stud.code === student.code ? (
                                <p key={stud.code}>
                                  {id.section.name + "  " + id.year.year}
                                </p>
                              ) : null
                            )
                          )}
                        </td>

                        <td>
                          <Button
                            className="btn-icon btn"
                            variant="danger"
                            onClick={() => this.deleteStudent(student.code)}
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
                              <Modal.Title as="h5">edit student</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                              <ValidationForm
                                onSubmit={this.handleSubmit}
                                onErrorSubmit={this.handleErrorSubmit}
                              >
                                <Form.Row>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="firstName">
                                      First name
                                    </Form.Label>
                                    <TextInput
                                      name="firstName"
                                      id="firstName"
                                      placeholder="First Name"
                                      required
                                      value={
                                        !this.state.firstName.length
                                          ? student.firstName
                                          : this.state.firstName
                                      }
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="lastName">
                                      Last name
                                    </Form.Label>
                                    <TextInput
                                      name="lastName"
                                      id="lastName"
                                      placeholder="Last Name"
                                      value={
                                        !this.state.lastName.length
                                          ? student.lastName
                                          : this.state.lastName
                                      }
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>

                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="birthDate">
                                      Birth date
                                    </Form.Label>
                                    <TextInput
                                      name="birthDate"
                                      id="birthDate"
                                      placeholder="birth Date"
                                      value={
                                        !this.state.birthDate.length
                                          ? student.birthDate
                                          : this.state.birthDate
                                      }
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="status">
                                      Class
                                    </Form.Label>
                                    <TextInput
                                      name="status"
                                      id="status"
                                      placeholder="student Class "
                                      required
                                      errorMessage={{
                                        required: "status is required",
                                        pattern: "status is invalid."
                                      }}
                                      value={
                                        !this.state.status.length
                                          ? student.status
                                          : this.state.status
                                      }
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
                                        this.editStudent(student.code);
                                      }}
                                      type="submit"
                                    >
                                      edit student
                                    </Button>
                                  </Form.Group>
                                </Form.Row>
                              </ValidationForm>
                            </Modal.Body>
                            <Modal.Footer>
                              <Button
                                variant="secondary"
                                onClick={() =>
                                  this.setState({ isVertically2: false })
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

export default Students;
