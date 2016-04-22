'use strict';

describe('Controller Tests', function() {

    describe('Treatment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTreatment, MockSiteData;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockSiteData = jasmine.createSpy('MockSiteData');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Treatment': MockTreatment,
                'SiteData': MockSiteData
            };
            createController = function() {
                $injector.get('$controller')("TreatmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vivaxDataManagerApp:treatmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
