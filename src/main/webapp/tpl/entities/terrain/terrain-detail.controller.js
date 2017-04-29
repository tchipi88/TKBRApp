(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainDetailController', TerrainDetailController);

    TerrainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Terrain' ,'Unite','ProduitCategorie'];

    function TerrainDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Terrain ,Unite,ProduitCategorie) {
        var vm = this;

        vm.terrain = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:terrainUpdate', function(event, result) {
            vm.terrain = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
