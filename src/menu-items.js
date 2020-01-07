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
          id: "students",
          title: "students",
          type: "item",
          url: "/students",
          classes: "nav-item",
          icon: "feather icon-users"
        },
        {
          id: "teachers",
          title: "teachers",
          type: "item",
          url: "/teachers",
          icon: "feather icon-briefcase"
        },
        {
          id: "administrative managers",
          title: "administrative management",
          type: "item",
          url: "/administrative-managers",
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
