export default {
  items: [
    {
      id: "pages",
      title: "Menu",
      type: "group",
      icon: "icon-pages",
      children: [
        {
          id: "Home",
          title: "Home",
          type: "item",
          url: "/Home",
          classes: "nav-item",
          icon: "feather icon-home"
        },
        {
          id: "Clients",
          title: "Clients",
          type: "item",
          url: "/clients",
          classes: "nav-item",
          icon: "feather icon-users"
        },
        {
          id: "Cars",
          title: "Cars",
          type: "item",
          url: "/cars",
          icon: "feather icon-briefcase"
        },
        {
          id: "Rentals",
          title: "Rentals",
          type: "item",
          url: "/rentals",
          icon: "feather icon-eye"
        },
        {
          id: "statistics",
          title: "statistics",
          type: "item",
          url: "/Statistics",
          icon: "feather icon-activity"
        },
        {
          id: "settings",
          title: "settings",
          type: "item",
          url: "/settings",
          icon: "feather icon-settings"
        }
      ]
    }
  ]
};
