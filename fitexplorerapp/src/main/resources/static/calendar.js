document.addEventListener('DOMContentLoaded', function() {
  function parseUrlHash() {
      const hash = window.location.hash.substring(1); // Remove the '#' character
      const [view, date] = hash.split('/');
      return { view, date };
    }

  fetch('activities/list')
    .then(response => response.json())
    .then(data => {
      // Transform your data to the format expected by FullCalendar
      const events = data.map(activity => ({
        id: activity.id,
        title: activity.activityName || 'Unknown Activity',
        start: moment.unix(activity.startTime).toDate(),
        end: moment.unix(activity.startTime + activity.duration).toDate(),
        url: 'region.html?id=' + activity.id // You can link to the original file or provide more details here
      }));

      const { view, date } = parseUrlHash();

      $('#calendar').fullCalendar({
        events: events,
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'month,agendaWeek,agendaDay'
        },
        defaultView : view,
        defaultDate : date,
        viewRender: function(view, element) {
              // Check if the event was triggered by the back button
              const isBackButton = event && event.state === null;

              // Update the browser's history state with the current view and date
              const stateObject = {
                view: view.name,
                date: view.start.format(),
                scrollPosition: $('#calendar').scrollTop()
              };

              const existingHistory = history.state ? history.state : {};

              const combinedHistory = { ...existingHistory, ...stateObject };



              // Check if the state is not null before pushing it to history
              if (!isBackButton) {
                const title = 'Calendar View: ' + stateObject.view;
                const url = window.location.href.split('#')[0] + '#' + stateObject.view + '/' + stateObject.date;
                history.pushState(combinedHistory, title, url);
              }
          }
      });
    });
});
