'use strict';

describe('Controller Tests', function() {

    describe('SiteData Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSiteData, MockCategory, MockLocation, MockStudy, MockTreatment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSiteData = jasmine.createSpy('MockSiteData');
            MockCategory = jasmine.createSpy('MockCategory');
            MockLocation = jasmine.createSpy('MockLocation');
            MockStudy = jasmine.createSpy('MockStudy');
            MockTreatment = jasmine.createSpy('MockTreatment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SiteData': MockSiteData,
                'Category': MockCategory,
                'Location': MockLocation,
                'Study': MockStudy,
                'Treatment': MockTreatment
            };
            createController = function() {
                $injector.get('$controller')("SiteDataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vivaxDataManagerApp:siteDataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
