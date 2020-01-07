import React, {Component} from 'react';
import {Row, Col, Card, Table, Tab, Nav} from 'react-bootstrap';

import Aux from "../../hoc/_Aux";
import AmChartYearlySummary from "../Widget/Chart/AmChartYearlySummary";
import shape5 from '../../assets/images/widget/shape5.png';
import DEMO from "../../store/constant";
import AmChartEarnings1 from "../Widget/Chart/AmChartEarnings1";
import axios from "axios";
import * as moment from "moment";
import { Container, ButtonDropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';

class Statistics extends Component {
    constructor(props) {
        super(props);
        this.toggle1 = this.toggle1.bind(this);
        this.select1 = this.select1.bind(this);
        this.toggle2 = this.toggle2.bind(this);
        this.select2 = this.select2.bind(this);
        this.state = {
            dropdownOpen1: false,
            dropdownOpen2: false,
            section:"",
            year : "",
            students:[],
            failedStudents:[],
            studentsClasses:[],
            passedStudents:[],
            postponedStudents:[],
            InProgressStudents:[],
            sections:[],
            years:[],
            stud: [],
            abscentStudents:[]
          
        
        };
      }
      toggle1() {
        this.setState({
          dropdownOpen1: !this.state.dropdownOpen1
        });
      }
      toggle2() {
        this.setState({
          dropdownOpen2: !this.state.dropdownOpen2
        });
      }
    
      select1(event) {
        console.log(this.state.section)
        this.setState({
          dropdownOpen1: !this.state.dropdownOpen1,
          section: event.target.innerText
        });
        let students =[]
     
        students = this.state.studentsClasses.filter(item=>(item.year.year === this.state.year && 
            item.section.name === this.state.section 
            ? item : null
            ))
        this.setState({stud : students.map(item=>(item.students))})
        console.log("stud : ",this.state.stud);
      
      }
      select2(event) {
        console.log(this.state.year);
        this.setState({
          dropdownOpen: !this.state.dropdownOpen,
          year: event.target.innerText
        });
        let students = []    
        students = this.state.studentsClasses.filter(item=>(item.year.year === this.state.year && 
            item.section.name === this.state.section 
            ? item : null
            ))
        this.setState({stud : students.map(item=>(item.students))})
        console.log("students : ",this.state.stud);
       
      }
    componentDidMount() {
        this.getStudents();
        this.getStudentsClasses();
        this.getFailedStudents();
        this.getPassedStudents();
        this.getPostponedStudents();
        this.getInProgressStudents();
        this.getSections();
        this.getYears();
        this.getabscentStudents();
      }
      getabscentStudents=()=> {
        axios
        .get("http://localhost:8080/Absences")
        .then(response => {
          this.setState({ abscentStudents: response.data });
          console.log(response.data);
          console.log("ok");
        })
        .catch(function(error) {
          console.log(error);
        });

      }
      getSections=()=> {
        axios
        .get("http://localhost:8080/Sections")
        .then(response => {
          this.setState({ sections: response.data });
          console.log(response.data);
          console.log("ok");
        })
        .catch(function(error) {
          console.log(error);
        });

      }
      getYears=()=> {
        axios
        .get("http://localhost:8080/UniversitairyYears")
        .then(response => {
          this.setState({ years: response.data });
          console.log(response.data);
          console.log("ok");
        })
        .catch(function(error) {
          console.log(error);
        });

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
      getFailedStudents = () => {
        axios
          .get("http://localhost:8080/Students/Status/FAILED")
          .then(response => {
            this.setState({ failedStudents: response.data });
            console.log(response.data);
            console.log("ok");
          })
          .catch(function(error) {
            console.log(error);
          });
      };
      getPassedStudents = () => {
        axios
          .get("http://localhost:8080/Students/Status/PASSED")
          .then(response => {
            this.setState({ passedStudents: response.data });
            console.log(response.data);
            console.log("ok");
          })
          .catch(function(error) {
            console.log(error);
          });
      };
      getInProgressStudents = () => {
        axios
          .get("http://localhost:8080/Students/Status/PASSED")
          .then(response => {
            this.setState({ InProgressStudents: response.data });
            console.log(response.data);
            console.log("ok");
          })
          .catch(function(error) {
            console.log(error);
          });
      };
      getPostponedStudents = () => {
        axios
          .get("http://localhost:8080/Students/Status/POSTPONED")
          .then(response => {
            this.setState({ postponedStudents: response.data });
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
      
      getPourcentage =(a,b)=>{
          let val = a*100/b;
          return val.toFixed(2)
      };
     
    render() {
        return (
            <Aux>
                <Row>
               
                <Col md={12} >
                <ButtonDropdown direction="down"style={{marginBottom:"10px",marginRight:"5px"}} isOpen={this.state.dropdownOpen1} toggle={this.toggle1}>
                    <DropdownToggle caret size="lg" color="warning">{this.state.section.length ? this.state.section : "Select Section"}</DropdownToggle>
                    <DropdownMenu>
                    {this.state.sections.map(section => (
                                    <DropdownItem
                                      key={section.code}
                                      onClick={this.select1}
                                    >
                                      {section.name}
                                    </DropdownItem>
                                  ))}
                     </DropdownMenu>
                </ButtonDropdown>
                <ButtonDropdown style={{marginBottom:"10px"}} isOpen={this.state.dropdownOpen2} toggle={this.toggle2}>
                    <DropdownToggle caret size="lg" color="warning">{this.state.year.length ? this.state.year : "Select Year"}</DropdownToggle>
                    <DropdownMenu>
                    {this.state.years.map(classe => (
                                    <DropdownItem
                                      key={classe.year}
                                      onClick={this.select2}
                                    >
                                      {
                                        classe.year}
                                    </DropdownItem>
                                  ))}
                     </DropdownMenu>
                </ButtonDropdown>
                </Col>
                    <Col md={4} xl={3}>
                        <Card className='Online-Order'>
                            <Card.Body>
                                <h5>Passed </h5>
                                <h6 className="text-muted d-flex align-items-center justify-content-between m-t-30"><span className="float-right f-18 text-c-green">{this.state.passedStudents.length} / {this.state.students.length}</span></h6>
                                <div className="progress mt-3">
                                    <div className="progress-bar progress-c-theme" role="progressbar" style={{width: '65%', height: '6px'}} aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"/>
                                </div>
                                <span className="text-muted mt-2 d-block">{this.getPourcentage(this.state.passedStudents.length,this.state.students.length)}% Passed</span>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md={4} xl={3}>
                        <Card className='Online-Order'>
                            <Card.Body>
                                <h5>Postponed  </h5>
                                <h6 className="text-muted d-flex align-items-center justify-content-between m-t-30"><span className="float-right f-18 text-c-green">{this.state.postponedStudents.length} / {this.state.students.length}</span></h6>
                                <div className="progress mt-3">
                                    <div className="progress-bar progress-c-theme" role="progressbar" style={{width: '65%', height: '6px'}} aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"/>
                                </div>
                                <span className="text-muted mt-2 d-block">{this.getPourcentage(this.state.postponedStudents.length,this.state.students.length)}% postponed</span>
                            </Card.Body>
                        </Card>
                    </Col>
                  
                    <Col xl={3}>
                        <Card className='Online-Order'>
                            <Card.Body>
                                <h5>Failed </h5>
                                <h6 className="text-muted d-flex align-items-center justify-content-between m-t-30"><span className="float-right f-18 text-c-blue">{this.state.failedStudents.length} / {this.state.students.length}</span></h6>
                                <div className="progress mt-3">
                                    <div className="progress-bar progress-c-blue" role="progressbar" style={{width: '40%', height: '6px'}} aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"/>
                                </div>
                                <span className="text-muted mt-2 d-block">10% failed</span>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md={4} xl={3}>
                        <Card className='Online-Order'>
                            <Card.Body>
                                <h5>In Progress </h5>
                                <h6 className="text-muted d-flex align-items-center justify-content-between m-t-30"><span className="float-right f-18 text-c-purple">{this.state.InProgressStudents.length} / {this.state.students.length}</span></h6>
                                <div className="progress mt-3">
                                    <div className="progress-bar progress-c-theme2" role="progressbar" style={{width: '50%', height: '6px'}} aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"/>
                                </div>
                                <span className="text-muted mt-2 d-block">{this.getPourcentage(this.state.InProgressStudents.length,this.state.students.length)}% In Progress</span>
                            </Card.Body>
                        </Card>
                    </Col>
                    
                    <Col xl={8} md={6}>
                        <Card className='code-table'>
                            <Card.Header>
                                <Card.Title as='h5'>List of Students {this.state.section + " "+ this.state.year}</Card.Title>
                            </Card.Header>
                            <Card.Body className='pb-0'>
                                <Table responsive >
                                <thead>
                                <tr>
                                  <th>First Name</th>
                                  <th>Last Name</th>
                                  <th>Birth Date</th>
                                  <th>Status</th>
                                </tr>
                              </thead>
                              <tbody>
                    {this.state.stud.length ? this.state.stud.map(item => (
                        item.map((student)=> ( 
                        <tr key={student.code}>
                            <td>{student.firstName}</td>
                            <td>{student.lastName}</td>
                            <td>{moment(student.birthDate).format("YYYY/MM/DD")}</td>
                            <td>{student.status}</td>
                          </tr>))
                     
                        )) :  this.state.students.map(student => (
                            <tr key={student.code}>
                              <td>{student.firstName}</td>
                              <td>{student.lastName}</td>
                              <td>{moment(student.birthDate).format("YYYY/MM/DD")}</td>
                            <td>{student.status}</td>
                              </tr>
                              ))} 

                                    </tbody>
                                </Table>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col xl={4} md={6}>
                        <Card className='theme-bg earning-date'>
                            <Card.Header className='borderless'>
                                <Card.Title as='h5' className='text-white'>Total Number</Card.Title>
                            </Card.Header>
                            <Card.Body>
                                <div className='bd-example bd-example-tabs'>
                                    <Tab.Container id="left-tabs-example" defaultActiveKey="mon">
                                        <Tab.Content>
                                            <Tab.Pane eventKey="mon">
                                                <h2 className="text-white mb-3 f-w-300">359,234<i className="feather icon-arrow-up"></i></h2>
                                                <span className="text-white mb-4 d-block">Student total Number</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="tue">
                                                <h2 className="text-white mb-3 f-w-300">222,586<i className="feather icon-arrow-down"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="wed">
                                                <h2 className="text-white mb-3 f-w-300">859,745<i className="feather icon-arrow-up"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="thu">
                                                <h2 className="text-white mb-3 f-w-300">785,684<i className="feather icon-arrow-up"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="fri">
                                                <h2 className="text-white mb-3 f-w-300">123,486<i className="feather icon-arrow-down"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="sat">
                                                <h2 className="text-white mb-3 f-w-300">762,963<i className="feather icon-arrow-up"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                            <Tab.Pane eventKey="sun">
                                                <h2 className="text-white mb-3 f-w-300">984,632<i className="feather icon-arrow-down"></i></h2>
                                                <span className="text-white mb-4 d-block">TOTAL EARNINGS</span>
                                            </Tab.Pane>
                                        </Tab.Content>
                                        <Nav variant="pills" className='align-items-center justify-content-center'>
                                            <Nav.Item>
                                                <Nav.Link eventKey="mon">Mon</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="tue">Tue</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="wed">Wed</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="thu">thu</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="fri">Fri</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="sat">Sat</Nav.Link>
                                            </Nav.Item>
                                            <Nav.Item>
                                                <Nav.Link eventKey="sun">Sun</Nav.Link>
                                            </Nav.Item>
                                        </Nav>
                                    </Tab.Container>
                                </div>
                            </Card.Body>
                        </Card>
                        <Card className='theme-bg2'>
                            <Card.Body>
                                <div className="row align-items-center justify-content-center">
                                    <div className="col-auto">
                                        <img src={shape5} alt="activity-user" />
                                    </div>
                                    <div className="col">
                                        <h2 className="text-white f-w-300">375</h2>
                                        <h5 className="text-white">Teachers total Number</h5>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col xl={8} md={6}>
                        <Card className='code-table'>
                            <Card.Header>
                                <Card.Title as='h5'>List of abscent students</Card.Title>
                            </Card.Header>
                            <Card.Body className='pb-0'>
                                <Table responsive hover>
                                <thead>
                                <tr>
                                <th>date</th>
                                  <th>First Name</th>
                                  <th>Last Name</th>
                                  <th>Status</th>
                                </tr>
                              </thead>
                              <tbody>
                    {this.state.abscentStudents.map(stud => (
                        
                         <tr key={stud.code}>
                            <td>{moment(stud.date).format("YYYY/MM/DD")}</td>
                            <td>{stud.student.firstName}</td>
                            <td>{stud.student.lastName}</td>
                            <td>{stud.student.status}</td>
    
                         </tr>
                   
                        ))}

                                    </tbody>
                                </Table>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col xl={4}>
                        <Card>
                            <Card.Header>
                                <Card.Title as='h5'>success rate</Card.Title>
                                <Card.Text>Mon 15 - Sun 21</Card.Text>
                            </Card.Header>
                            <Card.Body>
                                <div className="earning-price mb-4">
                                    <h3 className="m-0 f-w-300">894.39</h3>
                                </div>
                                <AmChartEarnings1 height='235px'/>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Aux>
        );
    }
};

export default Statistics;