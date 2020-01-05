import React from "react";
import $ from "jquery";

window.jQuery = $;
window.$ = $;
global.jQuery = $;

const Home = React.lazy(() => import("./Demo/Containers/Home"));
const Students = React.lazy(() => import("./Demo/Containers/students"));
const Teachers = React.lazy(() => import("./Demo/Containers/teachers"));
const AdministrativeManagers = React.lazy(() =>
  import("./Demo/Containers/administrativeManagers")
);
const Statistics = React.lazy(() => import("./Demo/Dashboard/Statistics"));

const routes = [
  {
    path: "/home",
    exact: true,
    name: "Home",
    component: Home
  },
  { path: "/students", exact: true, name: "students", component: Students },
  { path: "/teachers", exact: true, name: "teachers", component: Teachers },
  {
    path: "/administrative-managers",
    exact: true,
    name: "administrativeManagers",
    component: AdministrativeManagers
  },
  {
    path: "/Statistics",
    exact: true,
    name: "Statistics",
    component: Statistics
  }
];

export default routes;
