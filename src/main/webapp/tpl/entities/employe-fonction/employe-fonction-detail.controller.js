(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeFonctionDetailController', EmployeFonctionDetailController);

    EmployeFonctionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'EmployeFonction' ];

    function EmployeFonctionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, EmployeFonction ) {
        var vm = this;

        vm.employeFonction = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:employeFonctionUpdate', function(event, result) {
            vm.employeFonction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
