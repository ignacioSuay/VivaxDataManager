'use strict';

describe('Controller Tests', function() {

    describe('Location Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLocation, MockSiteData;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLocation = jasmine.createSpy('MockLocation');
            MockSiteData = jasmine.createSpy('MockSiteData');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Location': MockLocation,
                'SiteData': MockSiteData
            };
            createController = function() {
                $injector.get('$controller')("LocationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vivaxDataManagerApp:locationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
