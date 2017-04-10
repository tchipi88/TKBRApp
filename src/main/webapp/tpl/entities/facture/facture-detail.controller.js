(function() {
    'use strict';

    angular
        .module('app')
        .controller('FactureDetailController', FactureDetailController);

    FactureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Facture' ,'Vente'];

    function FactureDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Facture ,Vente) {
        var vm = this;

        vm.facture = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:factureUpdate', function(event, result) {
            vm.facture = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
