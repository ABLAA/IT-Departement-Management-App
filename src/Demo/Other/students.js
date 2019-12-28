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

    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    phone: "",
    description: "",
    url: "",
    gear: "",
    basic: "",
    custom: "",
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
                        onClick={() => this.setState({ isVertically: true })}
                      >
                        <i className="feather icon-plus-square" />
                      </Button>
                      <Modal
                        centered
                        show={this.state.isVertically}
                        onHide={() => this.setState({ isVertically: false })}
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
                                  minLength="4"
                                  value={this.state.lastName}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
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
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="phone">Phone</Form.Label>
                                <MaskWithValidation
                                  name="phone"
                                  id="phone"
                                  placeholder="Contact Number"
                                  className="form-control"
                                  required
                                  validator={value =>
                                    value === "(123) 456-7890"
                                  }
                                  value={this.state.phone}
                                  onChange={this.handleChange}
                                  successMessage="Looks good!"
                                  errorMessage={{
                                    validator: "Please enter (123) 456-7890"
                                  }}
                                  mask={[
                                    "(",
                                    /[1-9]/,
                                    /[0-9]/,
                                    /[0-9]/,
                                    ")",
                                    " ",
                                    /[0-9]/,
                                    /[0-9]/,
                                    /[0-9]/,
                                    "-",
                                    /[0-9]/,
                                    /[0-9]/,
                                    /[0-9]/,
                                    /[0-9]/
                                  ]}
                                  autoComplete="off"
                                />
                              </Form.Group>
                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="url">URL</Form.Label>
                                <TextInput
                                  name="url"
                                  id="url"
                                  type="url"
                                  placeholder="Website"
                                  required
                                  pattern="^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$"
                                  errorMessage={{
                                    required: "URL is required",
                                    pattern: "URL is invalid."
                                  }}
                                  value={this.state.url}
                                  onChange={this.handleChange}
                                  autoComplete="off"
                                />
                              </Form.Group>

                              <Form.Group as={Col} sm="6" className="mb-5">
                                <Form.Label htmlFor="description">
                                  Switch
                                </Form.Label>
                                <div className="switch">
                                  <Checkbox
                                    name="check-me"
                                    label=""
                                    id="check-me"
                                    defaultValue={this.state.checkMeSwitch}
                                    required
                                    inline
                                    onChange={this.handleCheckboxChange}
                                  />
                                  <Form.Label>Check me</Form.Label>
                                </div>
                              </Form.Group>

                              <Form.Group as={Col} md="6">
                                <Form.Label htmlFor="upload_avatar">
                                  Upload Avatar
                                </Form.Label>
                                <div className="custom-file">
                                  <FileInput
                                    name="avatar"
                                    id="avatar"
                                    required
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
                                <Button type="submit">Submit</Button>
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
                      <th>Username</th>
                      <th>delete</th>
                      <th>update</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row">1</th>
                      <td>Mark</td>
                      <td>Otto</td>
                      <td>@mdo</td>
                      <td>
                        <Button className="btn-icon btn" variant="danger">
                          <i className="feather icon-trash" />
                        </Button>
                      </td>
                      <td>
                        <Button className="btn-icon btn" variant="secondary">
                          <i className="feather icon-edit" />
                        </Button>
                      </td>
                    </tr>
                    <tr>
                      <th scope="row">2</th>
                      <td>Jacob</td>
                      <td>Thornton</td>
                      <td>@fat</td>
                      <td>
                        <Button className="btn-icon btn" variant="danger">
                          <i className="feather icon-trash" />
                        </Button>
                      </td>
                      <td>
                        <Button className="btn-icon btn" variant="secondary">
                          <i className="feather icon-edit" />
                        </Button>
                      </td>
                    </tr>
                    <tr>
                      <th scope="row">3</th>
                      <td>Larry</td>
                      <td>the Bird</td>
                      <td>@twitter</td>
                      <td>
                        <Button className="btn-icon btn" variant="danger">
                          <i className="feather icon-trash" />
                        </Button>
                      </td>
                      <td>
                        <Button className="btn-icon btn" variant="secondary">
                          <i className="feather icon-edit" />
                        </Button>
                      </td>
                    </tr>
                    <tr>
                      <th scope="row">4</th>
                      <td>aa</td>
                      <td>the Bird</td>
                      <td>@twitter</td>
                      <td>
                        <Button className="btn-icon btn" variant="danger">
                          <i className="feather icon-trash" />
                        </Button>
                      </td>
                      <td>
                        <Button className="btn-icon btn" variant="secondary">
                          <i className="feather icon-edit" />
                        </Button>
                      </td>
                    </tr>
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
