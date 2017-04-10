(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseMouvementDetailController', CaisseMouvementDetailController);

    CaisseMouvementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CaisseMouvement' ,'Caisse'];

    function CaisseMouvementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CaisseMouvement ,Caisse) {
        var vm = this;

        vm.caisseMouvement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:caisseMouvementUpdate', function(event, result) {
            vm.caisseMouvement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
