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
const Ecommerce = React.lazy(() => import("./Demo/Dashboard/Ecommerce"));

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
    path: "/Ecommerce",
    exact: true,
    name: "Ecommerce",
    component: Ecommerce
  }
];

export default routes;
