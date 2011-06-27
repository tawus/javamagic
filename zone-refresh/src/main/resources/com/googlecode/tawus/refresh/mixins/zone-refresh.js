if (!Tapestry.ZoneRefresh)
{
   Tapestry.ZoneRefresh = {};
}

Tapestry.Initializer.zoneRefresh =
      function(params)
      {
         if (Tapestry.ZoneRefresh[params.id])
         {
            // Timer already in use
            return;
         }

         var element = $(params.id);
         $T(element).zoneId = params.id;

         var keepUpdatingZone = function(e)
         {
            var zoneManager = Tapestry.findZoneManager(element);
            if (zoneManager == null)
            {
               e.stop();
               Tapestry.ZoneRefresh[params.id] = null;
               return;
            }

            zoneManager.updateFromURL(params.URL);

         };

         Tapestry.ZoneRefresh[params.id] =
               new PeriodicalExecuter(keepUpdatingZone, params.period);
      }

Event.observe(window, "beforeunload", function()
{
   if (Tapestry.ZoneRefresh)
   {
      for ( var propertyName in Tapestry.ZoneRefresh)
      {
         var property = Tapestry.ZoneRefresh[propertyName];
         property.stop();
      }
   }
});
