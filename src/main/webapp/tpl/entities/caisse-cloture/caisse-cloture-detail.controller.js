(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseClotureDetailController', CaisseClotureDetailController);

    CaisseClotureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CaisseCloture' ,'Caisse'];

    function CaisseClotureDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CaisseCloture ,Caisse) {
        var vm = this;

        vm.caisseCloture = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:caisseClotureUpdate', function(event, result) {
            vm.caisseCloture = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
