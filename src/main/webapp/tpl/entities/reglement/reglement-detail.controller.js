(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ReglementDetailController', ReglementDetailController);

    ReglementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reglement', 'Commande'];

    function ReglementDetailController($scope, $rootScope, $stateParams, previousState, entity, Reglement, Commande) {
        var vm = this;

        vm.reglement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:reglementUpdate', function(event, result) {
            vm.reglement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
