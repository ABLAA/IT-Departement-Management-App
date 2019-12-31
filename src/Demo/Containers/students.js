import React, { Component } from "react";
import { Row, Col, Card, Table, Button, Modal, Form } from "react-bootstrap";
import {
  ValidationForm,
  TextInput,
  BaseFormControl,
  SelectGroup,
  FileInput,
  Checkbox,
  Radio
} from "react-bootstrap4-form-validation";
import MaskedInput from "react-text-mask";
import validator from "validator";
import Aux from "../../hoc/_Aux";

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
  state = {
    isVertically: false,
    isVertically1: false,

    students: [
      {
        code: 1,
        firstName: "abla",
        lastName: "AMMAMI",
        birthDate: "08/08/1996 ",
        studentClass: "IF5"
      },
      {
        code: 2,
        firstName: "ameni",
        lastName: "Ben amor",
        birthDate: "08/08/1996 ",
        studentClass: "IF5"
      },
      {
        code: 3,
        firstName: "Alla",
        lastName: "ATTIA",
        birthDate: "08/08/1996 ",
        studentClass: "IF5"
      }
    ],

    firstName: "",
    lastName: "",
    birthDate: "",
    studentClass: "",
    chkBasic: false,
    chkCustom: false,
    checkMeSwitch: false,
    showModal: false
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
  deleteStudent = code => {
    let students = this.state.students.filter(student => student.code !== code);
    this.setState({ students });
  };
  addStudent = () => {
    let students = this.state.students;
    students.push({
      code: Math.random(),
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      birthDate: this.state.birthDate,
      studentClass: this.state.studentClass
    });
    console.log(students);
    this.setState({ students });
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
            studentClass: this.state.studentClass.length
              ? this.state.studentClass
              : student.studentClass
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
    //       studentClass: this.state.studentClass
    //     };
    //   }
    // });
    console.log(students, this.state.firstName);
    this.setState({ students });
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
                              {/* <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="email">Email</Form.Label>
                                <TextInput
                                  name="email"
                                  id="email"
                                  type="email"
                                  placeholder="Email Address"
                                  validator={validator.isEmail}
                                  errorMessage={{
                                    validator: "Please enter a valid email"
                                  }}
                                  value={this.state.email}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group> */}
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="birthDate">
                                  Birth date
                                </Form.Label>
                                <TextInput
                                  name="birthDate"
                                  id="birthDate"
                                  placeholder="birth Date"
                                  value={this.state.birthDate}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="studentClass">
                                  Class
                                </Form.Label>
                                <TextInput
                                  name="studentClass"
                                  id="studentClass"
                                  placeholder="student Class "
                                  required
                                  errorMessage={{
                                    required: "studentClass is required",
                                    pattern: "studentClass is invalid."
                                  }}
                                  value={this.state.studentClass}
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
                              this.setState({ isVertically: false })
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
                      <th>birth Date</th>
                      <th>class</th>
                      <th>delete</th>
                      <th>update</th>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.students.map(student => (
                      <tr key={student.code}>
                        <th scope="row">1</th>
                        <td>{student.firstName}</td>
                        <td>{student.lastName}</td>
                        <td>{student.birthDate}</td>
                        <td>{student.studentClass}</td>
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
                                    <Form.Label htmlFor="studentClass">
                                      Class
                                    </Form.Label>
                                    <TextInput
                                      name="studentClass"
                                      id="studentClass"
                                      placeholder="student Class "
                                      required
                                      errorMessage={{
                                        required: "studentClass is required",
                                        pattern: "studentClass is invalid."
                                      }}
                                      value={
                                        !this.state.studentClass.length
                                          ? student.studentClass
                                          : this.state.studentClass
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

export default Students;
