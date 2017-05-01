(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainCommandeDetailController', TerrainCommandeDetailController);

    TerrainCommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'TerrainCommande' ,'Fournisseur','Terrain','TerrainSuivi','Client'];

    function TerrainCommandeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, TerrainCommande ,Fournisseur,Terrain,TerrainSuivi,Client) {
        var vm = this;

        vm.terrainCommande = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:terrainCommandeUpdate', function(event, result) {
            vm.terrainCommande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
