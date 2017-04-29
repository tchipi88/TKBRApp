(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainSuiviDeleteController',TerrainSuiviDeleteController);

    TerrainSuiviDeleteController.$inject = ['$uibModalInstance', 'entity', 'TerrainSuivi'];

    function TerrainSuiviDeleteController($uibModalInstance, entity, TerrainSuivi) {
        var vm = this;

        vm.terrainSuivi = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TerrainSuivi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
