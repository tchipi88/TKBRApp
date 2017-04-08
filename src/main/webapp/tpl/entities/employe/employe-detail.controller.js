(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('EmployeDetailController', EmployeDetailController);

    EmployeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employe', 'EmployeFonction'];

    function EmployeDetailController($scope, $rootScope, $stateParams, previousState, entity, Employe, EmployeFonction) {
        var vm = this;

        vm.employe = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:employeUpdate', function(event, result) {
            vm.employe = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
