(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainSuiviDetailController', TerrainSuiviDetailController);

    TerrainSuiviDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'TerrainSuivi' ];

    function TerrainSuiviDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, TerrainSuivi ) {
        var vm = this;

        vm.terrainSuivi = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:terrainSuiviUpdate', function(event, result) {
            vm.terrainSuivi = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
