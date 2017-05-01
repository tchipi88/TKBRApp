(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainDeleteController',TerrainDeleteController);

    TerrainDeleteController.$inject = ['$uibModalInstance', 'entity', 'Terrain'];

    function TerrainDeleteController($uibModalInstance, entity, Terrain) {
        var vm = this;

        vm.terrain = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Terrain.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
