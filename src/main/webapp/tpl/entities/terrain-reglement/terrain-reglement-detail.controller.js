(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainReglementDetailController', TerrainReglementDetailController);

    TerrainReglementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'TerrainReglement' ,'TerrainCommande'];

    function TerrainReglementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, TerrainReglement ,TerrainCommande) {
        var vm = this;

        vm.terrainReglement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:terrainReglementUpdate', function(event, result) {
            vm.terrainReglement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
