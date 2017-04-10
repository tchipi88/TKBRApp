(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDepartementDeleteController',EmployeDepartementDeleteController);

    EmployeDepartementDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmployeDepartement'];

    function EmployeDepartementDeleteController($uibModalInstance, entity, EmployeDepartement) {
        var vm = this;

        vm.employeDepartement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmployeDepartement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
