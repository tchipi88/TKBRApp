(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainReglementDeleteController',TerrainReglementDeleteController);

    TerrainReglementDeleteController.$inject = ['$uibModalInstance', 'entity', 'TerrainReglement'];

    function TerrainReglementDeleteController($uibModalInstance, entity, TerrainReglement) {
        var vm = this;

        vm.terrainReglement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TerrainReglement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
