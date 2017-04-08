(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('PvReceptionDeleteController',PvReceptionDeleteController);

    PvReceptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PvReception'];

    function PvReceptionDeleteController($uibModalInstance, entity, PvReception) {
        var vm = this;

        vm.pvReception = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PvReception.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
