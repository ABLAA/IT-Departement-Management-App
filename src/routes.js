import React from "react";
import $ from "jquery";

window.jQuery = $;
window.$ = $;
global.jQuery = $;

const Home = React.lazy(() => import("./Demo/Containers/Home"));
const Cars = React.lazy(() => import("./Demo/Containers/cars"));
const Clients = React.lazy(() => import("./Demo/Containers/clients"));
const Rental = React.lazy(() => import("./Demo/Containers/rental"));
const Statistics = React.lazy(() => import("./Demo/Dashboard/Statistics"));

const routes = [
  {
    path: "/home",
    exact: true,
    name: "Home",
    component: Home
  },
  { path: "/cars", exact: true, name: "cars", component: Cars },
  { path: "/clients", exact: true, name: "clients", component: Clients },
  {
    path: "/rental",
    exact: true,
    name: "rental",
    component: Rental
  },
  {
    path: "/Statistics",
    exact: true,
    name: "Statistics",
    component: Statistics
  }
];

export default routes;
