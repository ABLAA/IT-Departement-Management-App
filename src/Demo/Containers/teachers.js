import React, { Component } from "react";
import { Row, Col, Card, Table, Button, Modal, Form } from "react-bootstrap";
import {
  ValidationForm,
  TextInput,
  FileInput
} from "react-bootstrap4-form-validation";
import Aux from "../../hoc/_Aux";

class Teachers extends Component {
  state = {
    isVertically: false,
    isVertically1: false,

    teachers: [
      {
        code: 1,
        firstName: "sadok",
        lastName: "YAHIA",
        birthDate: "08/08/1996 ",
        departement: "IF5"
      },
      {
        code: 2,
        firstName: "ahlem",
        lastName: "Ben chrifa",
        birthDate: "08/08/1996 ",
        departement: "IF5"
      },
      {
        code: 3,
        firstName: "heithem",
        lastName: "ABBES",
        birthDate: "08/08/1996 ",
        departement: "IF5"
      }
    ],

    firstName: "",
    lastName: "",
    birthDate: "",
    departement: "",
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
  deleteteacher = code => {
    let teachers = this.state.teachers.filter(teacher => teacher.code !== code);
    this.setState({ teachers });
  };
  addteacher = () => {
    let teachers = this.state.teachers;
    teachers.push({
      code: Math.random(),
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      birthDate: this.state.birthDate,
      departement: this.state.departement
    });
    console.log(teachers);
    this.setState({ teachers });
  };
  editteacher = code => {
    let teachers = this.state.teachers.map(teacher =>
      teacher.code === code
        ? {
            code: Math.random(),
            firstName: this.state.firstName.length
              ? this.state.firstName
              : teacher.firstName,
            lastName: this.state.lastName.length
              ? this.state.lastName
              : teacher.lastName,
            birthDate: this.state.birthDate.length
              ? this.state.birthDate
              : teacher.birthDate,
            departement: this.state.departement.length
              ? this.state.departement
              : teacher.departement
          }
        : teacher
    );
    // teachers = teachers.map(teacher => {
    //   if (teacher.code === code) {
    //     return {
    //       code: Math.random(),
    //       firstName: this.state.firstName,
    //       lastName: this.state.lastName,
    //       birthDate: this.state.birthDate,
    //       departement: this.state.departement
    //     };
    //   }
    // });
    console.log(teachers, this.state.firstName);
    this.setState({ teachers });
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
                      <h5>List of teachers</h5>
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
                          <Modal.Title as="h5">Add teacher</Modal.Title>
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
                                <Form.Label htmlFor="departement">
                                  Class
                                </Form.Label>
                                <TextInput
                                  name="departement"
                                  id="departement"
                                  placeholder="teacher Class "
                                  required
                                  errorMessage={{
                                    required: "departement is required",
                                    pattern: "departement is invalid."
                                  }}
                                  value={this.state.departement}
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
                                    this.addteacher();
                                  }}
                                  type="submit"
                                >
                                  add teacher
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
                    {this.state.teachers.map(teacher => (
                      <tr key={teacher.code}>
                        <th scope="row">1</th>
                        <td>{teacher.firstName}</td>
                        <td>{teacher.lastName}</td>
                        <td>{teacher.birthDate}</td>
                        <td>{teacher.departement}</td>
                        <td>
                          <Button
                            className="btn-icon btn"
                            variant="danger"
                            onClick={() => this.deleteteacher(teacher.code)}
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
                              <Modal.Title as="h5">edit teacher</Modal.Title>
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
                                          ? teacher.firstName
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
                                          ? teacher.lastName
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
                                          ? teacher.birthDate
                                          : this.state.birthDate
                                      }
                                      onChange={this.handleChange}
                                      autoComplete="off"
                                    />
                                  </Form.Group>
                                  <Form.Group as={Col} md="6">
                                    <Form.Label htmlFor="departement">
                                      Class
                                    </Form.Label>
                                    <TextInput
                                      name="departement"
                                      id="departement"
                                      placeholder="teacher Class "
                                      required
                                      errorMessage={{
                                        required: "departement is required",
                                        pattern: "departement is invalid."
                                      }}
                                      value={
                                        !this.state.departement.length
                                          ? teacher.departement
                                          : this.state.departement
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
                                        this.editteacher(teacher.code);
                                      }}
                                      type="submit"
                                    >
                                      edit teacher
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

export default Teachers;
