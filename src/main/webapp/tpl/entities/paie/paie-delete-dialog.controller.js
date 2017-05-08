(function() {
    'use strict';

    angular
        .module('app')
        .controller('PaieDeleteController',PaieDeleteController);

    PaieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Paie'];

    function PaieDeleteController($uibModalInstance, entity, Paie) {
        var vm = this;

        vm.paie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Paie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
