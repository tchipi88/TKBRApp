(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessGroupDeleteController',AccessGroupDeleteController);

    AccessGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'AccessGroup'];

    function AccessGroupDeleteController($uibModalInstance, entity, AccessGroup) {
        var vm = this;

        vm.accessGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AccessGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
