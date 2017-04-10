(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionDeleteController',BonReceptionDeleteController);

    BonReceptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'BonReception'];

    function BonReceptionDeleteController($uibModalInstance, entity, BonReception) {
        var vm = this;

        vm.bonReception = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BonReception.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
